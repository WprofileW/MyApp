package org.example.controller;

import org.example.pojo.Result;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public <T> Result register(@RequestBody Map<T, T> params) {
        return userService.register(params);
    }

    @PostMapping("/login")
    public <T> Result login(@RequestBody Map<T, T> params) {
        return userService.login(params);
    }

    @PatchMapping("/updatePwd")
    public <T> Result updatePwd(
            @RequestBody Map<T, T> params,
            @RequestHeader("Authorization") String token) {
        return userService.updatePwd(params, token);
    }

    @PostMapping("/setUserInfo")
    public <T> Result setUserInfo(@RequestBody Map<T, T> params) {
        return userService.setUserInfo(params);
    }

    @GetMapping("/getUserInfo")
    public Result getUserInfo() {
        return userService.getUserInfo();
    }

    @PutMapping("/updateUserInfo")
    public <T> Result updateUserInfo(@RequestBody Map<T, T> params) {
        return userService.updateUserInfo(params);
    }
}