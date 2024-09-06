package com.demo1.blogspringboot.service;

import com.demo1.blogspringboot.entity.User;
import com.demo1.blogspringboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能:
 * 作者: wilhelmaoi
 * 目期: 2024年9月5日 23:26
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void insertUser(User user) {
        userMapper.insert(user);
    }

    public void updateUser(User user) {
        userMapper.update(user);
    }

    public void deleteUser(Integer id) {
        userMapper.delete(id);
    }


    public void batchDelete(List<Integer> ids) {
        for (Integer id : ids) {
            userMapper.delete(id);
        }

    }

    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }
    public List<User> selectByMore(String username, String name) {
        return userMapper.selectByMore(username,name);
    }

    public List<User> selectByLike(String username, String name) {
        return userMapper.selectByLike(username,name);
    }


    public PageInfo<User> selectByPage(String username, String name, int pageNum, int pageSize) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 查询数据
        List<User> userList = userMapper.selectByLike(username, name);
        // 使用PageInfo封装分页信息
        return new PageInfo<>(userList);
    }
}

