package com.demo1.blogspringboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能: 提供接口返回注释
 * 作者: wilhelmaoi
 * 目期: 2024年9月5日 09:56
 */
@RestController
public class WebController {

    @RequestMapping
    public String hello(){
        return "hellow aoi";

    }
}
