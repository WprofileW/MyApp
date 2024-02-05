package org.example.service;

import org.example.pojo.Result;

import java.util.Map;

public interface UserService {
    //注册
    <T> Result register(Map<T, T> params);

    //登录
    <T> Result login(Map<T, T> params);

    //更新密码
    <T> Result updatePwd(Map<T, T> params, String token);

    <T> Result setUserInfo(Map<T, T> params);

    //获取用户信息
    Result getUserInfo();

    //修改用户信息
    <T> Result updateUserInfo(Map<T, T> params);
}
