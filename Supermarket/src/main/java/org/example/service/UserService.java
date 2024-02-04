package org.example.service;

import org.example.pojo.Result;

import java.util.Map;

public interface UserService {
    //注册
    Result register(String username, String password);

    //登录
    Result<String> login(String username, String password);

    //更新密码
    Result updatePwd(Map<String, String> params, String token);

    //获取用户信息
    Result getUserInfo();

    <T> Result setUserInfo(Map<T,T> params);
}
