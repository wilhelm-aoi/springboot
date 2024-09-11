package com.demo1.blogspringboot.entity;

import com.demo1.blogspringboot.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 功能:
 * 作者: wilhelmaoi
 * 目期: 2024年9月6日 22:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomPage {
    private Integer total;
    private List dataList;
}
