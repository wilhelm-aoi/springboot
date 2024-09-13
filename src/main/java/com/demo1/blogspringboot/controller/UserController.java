package com.demo1.blogspringboot.controller;

import com.demo1.blogspringboot.common.Result;
import com.demo1.blogspringboot.entity.CustomPage;
import com.demo1.blogspringboot.entity.User;
import com.demo1.blogspringboot.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
        userService.insertUser(user);
        return Result.success();
    }

    /**
     * 更改用户信息
     */

    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        userService.updateUser(user);
        return Result.success();
    }

    /**
     * 删除用户信息
     * 单个删除
     */

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.deleteUser(id);
        return Result.success();
    }

    /**
     * 批量删除
     */

    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<Integer> ids) {
        userService.batchDelete(ids);
        return Result.success();
    }

    /**
     * 查询全部用户信息
     */

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<User> userAll = userService.selectAll();
        return Result.success(userAll);
    }

    /**
     * 根据ID查询用户信息
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        User user = userService.selectById(id);
        return Result.success(user);
    }
//    @GetMapping("/selectById/{username}")
//    public Result selectById(@PathVariable String username) {
//        User user = userService.selectByUsername(username);
//        return Result.success(user);
//    }

    /**
     * 多条件查询
     */
    @GetMapping("/selectByMore")
    public Result selectByMore(@RequestParam String username, @RequestParam String name) {
        List<User> userList = userService.selectByMore(username, name);
        return Result.success(userList);
    }


    /**
     * 多条件模糊查询用户信息
     */
    @GetMapping("/selectByLike")
    public Result selectByLike(@RequestParam String username, @RequestParam String name) {
        List<User> userList = userService.selectByLike(username, name);
        return Result.success(userList);

    }
    /**
     * 分页查询
     */
//    @GetMapping("/selectByPage")
//    public Result selectByPage(@RequestParam String username, @RequestParam String name) {
//        List<User> userList = userService.selectByLike(username, name);
//        return Result.success(userList);
//
//    }


    @GetMapping("/selectByPage")
    public Result getUsers(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(required = false) String username,
                           @RequestParam(required = false) String name) {
        // 调用Service层进行分页查询
        CustomPage page = userService.selectByPage(username, name, pageNum, pageSize);
        return Result.success(page);
    }



}
