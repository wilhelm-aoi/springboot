package com.demo1.blogspringboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo1.blogspringboot.common.Result;
import com.demo1.blogspringboot.entity.CustomPage;
import com.demo1.blogspringboot.entity.User;
import com.demo1.blogspringboot.service.UserService;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 功能:
 * 作者: wilhelmaoi
 * 目期: 2024年9月5日 23:22
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 新增用户信息
     */
    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        // 查询数据库中是否存在相同的用户名
        User existingUser = userService.selectByUsername(user.getUsername());
        if (existingUser != null) {
            return Result.error("用户名已存在");
        } else {
            // 如果不存在，保存用户
            userService.save(user);
            return Result.success();
        }
    }

    /**
     * 更改用户信息
     */

    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        userService.updateById(user);
        return Result.success();
    }

    /**
     * 删除用户信息
     * 单个删除
     */

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.removeById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */

    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<Integer> ids) {
        userService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询全部用户信息
     */

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<User> userAll = userService.list(new QueryWrapper<User>().orderByDesc( "id"));
        return Result.success(userAll);
    }

    /**
     * 根据ID查询用户信息
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        User user = userService.getById(id);
        return Result.success(user);
    }

//    @GetMapping("/selectById/{username}")
//    public Result selectById(@PathVariable String username) {
//        User user = userService.selectByUsername(username);
//        return Result.success(user);
//    }
//    /**
//     * 多条件查询
//     */
//    @GetMapping("/selectByMore")
//    public Result selectByMore(@RequestParam String username, @RequestParam String name) {
//        List<User> userList = userService.getBy(username, name);
//        return Result.success(userList);
//    }
//
//
//    /**
//     * 多条件模糊查询用户信息
//     */
//    @GetMapping("/selectByLike")
//    public Result selectByLike(@RequestParam String username, @RequestParam String name) {
//        List<User> userList = userService.selectByLike(username, name);
//        return Result.success(userList);
//
//    }
//    /**
//     * 分页查询
//     */
//    @GetMapping("/selectByPage")
//    public Result selectByPage(@RequestParam String username, @RequestParam String name) {
//        List<User> userList = userService.selectByLike(username, name);
//        return Result.success(userList);
//
//    }


    /**
     * 多条件模糊查询用户信息
     * pageNum 当前的页码
     * pageSize 每页查询的个数
     */
    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String username,
                               @RequestParam String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().orderByDesc("id");  // 默认倒序，让最新的数据在最上面
        queryWrapper.like(StrUtil.isNotBlank(username), "username", username);
        queryWrapper.like(StrUtil.isNotBlank(name), "name", name);
        // select * from user where username like '%#{username}%' and name like '%#{name}%'
        Page<User> page = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);
    }


    @GetMapping("/export")
    public void exportData(@RequestParam(required = false) String username,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) String ids,  //   1,2,3,4,5
                           HttpServletResponse response) throws IOException {
        ExcelWriter writer = ExcelUtil.getWriter(true);

        List<User> list;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(ids)) {     // ["1", "2", "3"]   => [1,2,3]
            List<Integer> idsArr1 = Arrays.stream(ids.split(",")).map(Integer::valueOf).collect(Collectors.toList());
            queryWrapper.in("id", idsArr1);
        } else {
            // 第一种全部导出或者条件导出
            queryWrapper.like(StrUtil.isNotBlank(username), "username", username);
            queryWrapper.like(StrUtil.isNotBlank(name), "name", name);
        }
        list = userService.list(queryWrapper);   // 查询出当前User表的所有数据
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("用户信息表", "UTF-8") + ".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream, true);
        writer.close();
        outputStream.flush();
        outputStream.close();
    }


}
