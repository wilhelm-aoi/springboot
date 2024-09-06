package com.demo1.blogspringboot.controller;

import com.demo1.blogspringboot.common.Result;
import org.springframework.web.bind.annotation.*;

/**
 * 功能: 提供接口返回数据
 * 作者: wilhelmaoi
 * 目期: 2024年9月5日 09:56
 */

@RequestMapping("/web")
@RestController
public class WebController {

    @RequestMapping("/hello")
    public Result hello() {
        return Result.success("Hello");

    }

    @PostMapping("/post")
    public Result post(@RequestBody Obj obj) {
        return Result.success(obj);

    }

    @PutMapping("/put")
    public Result put(@RequestBody Obj obj) {
        return Result.success(obj);

    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(id);
    }

}
