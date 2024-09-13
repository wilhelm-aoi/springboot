package com.demo1.blogspringboot.service;

import com.demo1.blogspringboot.entity.CustomPage;
import com.demo1.blogspringboot.entity.User;
import com.demo1.blogspringboot.exception.ServiceException;
import com.demo1.blogspringboot.mapper.UserMapper;
import com.demo1.blogspringboot.utils.TokenUtils;
import com.github.pagehelper.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;
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
//    public User selectByUsername(String username) {
//        return userMapper.selectByUsername(username);
//    }
    public List<User> selectByMore(String username, String name) {
        return userMapper.selectByMore(username,name);
    }

    public List<User> selectByLike(String username, String name) {
        return userMapper.selectByLike(username,name);
    }


    public CustomPage selectByPage(String username, String name, Integer pageNum, Integer pageSize) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 查询数据
        List<User> userList = userMapper.selectByLike(username, name);
        // 使用PageInfo封装分页信息
        PageInfo<User> pageInfo = new PageInfo<>(userList);


        // 返回分页信息：注意使用pageInfo.getTotal()获取总记录数
        return new CustomPage((int) pageInfo.getTotal(), pageInfo.getList());
    }
    // 验证用户是否合法
    public User login(User user) {
        // 根据用户名查询数据库的用户信息
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser == null){
            // 抛出自定义异常
            throw new ServiceException("用户名或密码错误");
        }
        if(!user.getPassword().equals(dbUser.getPassword())){
            throw new ServiceException("用户名或密码错误");

        }//生成token
        String token = TokenUtils.createToken(dbUser.getId().toString(), dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

    public User register(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser != null) {
            // 抛出一个自定义的异常
            throw new ServiceException("用户名已存在");
        }
        user.setName(user.getUsername());
        userMapper.insert(user);
        return user;
    }


}

