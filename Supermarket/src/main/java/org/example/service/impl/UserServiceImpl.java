package org.example.service.impl;

import org.example.mapper.ContactMapper;
import org.example.mapper.UserMapper;
import org.example.pojo.Contact;
import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.utils.JwtUtil;
import org.example.utils.Md5Util;
import org.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ContactMapper contactMapper;


    @Override
    public Result register(String username, String password) {
        //查询用户
        User user = userMapper.findByUserName(username);
        if (user == null) {
            //没有占用注册
            //加密
            String md5String = Md5Util.getMD5String(password);
            //添加
            userMapper.add(username, md5String);
            return Result.success();
        } else {
            //占用
            return Result.error("用户名已被占用");
        }
    }


    @Override
    public Result<String> login(String username, String password) {

        //根据用户名查询用户
        User loginUser = userMapper.findByUserName(username);
        //判断该用户是否存在
        if (loginUser == null) {
            return Result.error("用户名错误");
        }

        //判断密码是否正确  loginUser对象中的password是密文
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            //登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", loginUser.getUserId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);


            //把token存储到redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 1, TimeUnit.HOURS);

            userMapper.updateLastLoginTime(loginUser.getUsername());
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @Override
    public Result updatePwd(Map<String, String> params, String token) {
        //1.校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if (!StringUtils.hasLength(oldPwd)
                || !StringUtils.hasLength(newPwd)
                || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要的参数");
        }
        //原密码是否正确
        //调用userService根据用户名拿到原密码,再和old_pwd比对
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userMapper.findByUserName(username);
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("原密码填写不正确");
        }

        //newPwd和rePwd是否一样
        if (!rePwd.equals(newPwd)) {
            return Result.error("两次填写的新密码不一样");
        }

        //2.调用mapper完成密码更新
        Integer id = (Integer) map.get("userId");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd), id);

        //删除redis中对应的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }

    @Override
    public Result getUserInfo() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("userId");
        //获取联系方式
        Contact contact = contactMapper.getContactByUserId(userId);
        Map<String, String> infoMap = new HashMap<>();
        infoMap.put("name", contact.getName());
        infoMap.put("phone", contact.getPhone());
        infoMap.put("email", contact.getEmail());
        infoMap.put("address", contact.getAddress());
        return Result.success(infoMap);
    }

    @Override
    public <T> Result setUserInfo(Map<T, T> params) {
        String name = (String) params.get("name");
        String phone = (String) params.get("phone");
        String email = (String) params.get("email");
        String address = (String) params.get("address");
        String notes = (String) params.get("notes");
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("phone", phone);
        map.put("email", email);
        map.put("address", address);
        map.put("notes", notes);

        Map<String, Object> mapLocal = ThreadLocalUtil.get();
        Integer userId = (Integer) mapLocal.get("userId");
        contactMapper.insertContact(userId,name, phone, email, address, notes);

        return Result.success(map);
    }
}
