package com.demo1.blogspringboot.controller;

import cn.hutool.core.util.StrUtil;
import com.demo1.blogspringboot.common.Result;
import com.demo1.blogspringboot.entity.User;
import com.demo1.blogspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能: 提供接口返回数据
 * 作者: wilhelmaoi
 * 目期: 2024年9月5日 09:56
 */


@RestController
public class WebController {

    @Autowired
    UserService userService;

    @RequestMapping("/hello")
    public Result hello() {
        return Result.success("Hello");

    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())) {
            return Result.error("数据输入不合法");

        }
        user = userService.login(user);
        return Result.success(user);
    }
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword()) || StrUtil.isBlank(user.getRole())) {
            return Result.error("数据输入不合法");
        }
        if (user.getUsername().length() > 10 || user.getPassword().length() > 20) {
            return Result.error("数据输入不合法");
        }
        user = userService.register(user);
        return Result.success(user);
    }
}
