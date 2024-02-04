package org.example.controller;

import jakarta.validation.constraints.Pattern;
import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(
            @Pattern(regexp = "^\\S{5,16}$") String username,
            @Pattern(regexp = "^\\S{5,16}$") String password) {
        return userService.register(username, password);
    }

    @PostMapping("/login")
    public Result<String> login(
            @Pattern(regexp = "^\\S{5,16}$") String username,
            @Pattern(regexp = "^\\S{5,16}$") String password) {
        return userService.login(username, password);
    }


    @PostMapping("/setUserInfo")
    public <T> Result setUserInfo(
            @RequestBody Map<T, T> params) {
        return userService.setUserInfo(params);
    }

    @GetMapping("/getUserInfo")
    public Result<User> userInfo() {
        return userService.getUserInfo();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(
            @RequestBody Map<String, String> params,
            @RequestHeader("Authorization") String token) {
        return userService.updatePwd(params, token);
    }
}
