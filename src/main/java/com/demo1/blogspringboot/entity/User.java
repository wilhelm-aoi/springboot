package com.demo1.blogspringboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能: 实体类
 * 作者: wilhelmaoi
 * 目期: 2024年9月5日 23:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String avatar;
    private String role;
    private String token;

}
