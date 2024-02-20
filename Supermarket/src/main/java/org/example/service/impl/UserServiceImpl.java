package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.example.mapper.ContactMapper;
import org.example.mapper.UserMapper;
import org.example.pojo.Contact;
import org.example.pojo.PageBean;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ContactMapper contactMapper;

    @Override
    public <T> Result register(Map<T, T> params) {
        String username = (String) params.get("username");
        String password = (String) params.get("password");
        Integer roleId = (Integer) params.get("roleId");
        String realName = (String) params.get("realName");
        String phone = (String) params.get("phone");
        String email = (String) params.get("email");
        String address = (String) params.get("address");
        String notes = (String) params.get("notes");
        contactMapper.insertContact(username, realName, phone, email, address, notes);
        //查询用户
        User user = userMapper.findByUserName(username);
        if (user == null) {
            //没有占用注册
            //加密
            String md5String = Md5Util.getMD5String(password);
            //添加
            userMapper.add(username, md5String, roleId);
            return Result.success();
        } else {
            //占用
            return Result.error("用户名已被占用");
        }
    }

    @Override
    public <T> Result login(Map<T, T> params) {
        String username = (String) params.get("username");
        String password = (String) params.get("password");

        //根据用户名查询用户
        User loginUser = userMapper.findByUserName(username);
        //判断该用户是否存在
        if (loginUser == null) {
            return Result.error("用户名错误");
        }
        //判断密码是否正确
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            //登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", loginUser.getUserId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            //把token存储到redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            //设置1天过期
            operations.set(token, token, 1, TimeUnit.DAYS);
            //更新登陆时间
            userMapper.updateLastLoginTime(loginUser.getUsername());
            log.info("[CustomizedLogs]:-------欢迎{}登录-------", loginUser.getUsername());
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @Override
    public <T> Result updatePwd(Map<T, T> params, String token) {
        //1.校验参数
        String oldPwd = (String) params.get("old_pwd");
        String newPwd = (String) params.get("new_pwd");
        String rePwd = (String) params.get("re_pwd");
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
    public <T> Result setUserInfo(Map<T, T> params) {
        //从token获取username
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        //从params中获取个人信息
        String realName = (String) params.get("realName");
        String phone = (String) params.get("phone");
        String email = (String) params.get("email");
        String address = (String) params.get("address");
        String notes = (String) params.get("notes");
        contactMapper.insertContact(username, realName, phone, email, address, notes);

        //返回刚刚设置的个人信息
        Map<String, String> infoMap = new HashMap<>();
        infoMap.put("realName", realName);
        infoMap.put("phone", phone);
        infoMap.put("email", email);
        infoMap.put("address", address);
        infoMap.put("notes", notes);
        return Result.success(infoMap);
    }

    @Override
    public Result getUserInfo() {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        return Result.success(contactMapper.getContactByUsername(username));
    }

    @Override
    public <T> Result updateUserInfo(Map<T, T> params) {
        //从params中获取个人信息
        String username = (String) params.get("username");
        String realName = (String) params.get("realName");
        String phone = (String) params.get("phone");
        String email = (String) params.get("email");
        String address = (String) params.get("address");
        String notes = (String) params.get("notes");
        contactMapper.updateContact(username, realName, phone, email, address, notes);
        //返回刚刚设置的个人信息
        Map<String, String> infoMap = new HashMap<>();
        infoMap.put("realName", realName);
        infoMap.put("phone", phone);
        infoMap.put("email", email);
        infoMap.put("address", address);
        infoMap.put("notes", notes);
        return Result.success(infoMap);
    }

    @Override
    public <T> Result getAllUsers(Map<T, T> params) {
        Integer pageNum = (Integer) params.get("pageNum");
        Integer pageSize = (Integer) params.get("pageSize");
        //1.创建PageBean对象
        PageBean<Contact> pb = new PageBean<>();
        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum, pageSize);
        //3.调用mapper
        List<Contact> as = contactMapper.getAllUsers();
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<Contact> p = (Page<Contact>) as;
        //把数据填充到PageBean对象中
        pb.setTotal((int) p.getTotal());
        pb.setItems(p.getResult());
        return Result.success(pb);
    }
}