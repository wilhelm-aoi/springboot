package com.demo1.blogspringboot.mapper;

import com.demo1.blogspringboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface UserMapper {
    @Insert("insert into `user` (username,password,name,phone,email,address,avatar,role) " +
            "values (#{username},#{password},#{name},#{phone},#{email},#{address},#{avatar},#{role})")
    void insert(User user);

    @Update("update user set username = #{username},password = #{password}, name= #{name}," +
            "phone = #{phone},address =#{address},avatar =#{avatar},email = #{email} where id = #{id}")
    void update(User user);

    @Delete("delete from `user` where id = #{id}")
    void delete(Integer id);
    @Select("select * from `user`")
    List<User> selectAll();

    @Select("select * from `user` where id = #{id} order by id desc")
    User selectById(Integer id);

    @Select("select * from `user` where username = #{username} and name = #{name} order by id desc")
    List<User> selectByMore(@Param("username") String username, @Param("name") String name);

    @Select("select * from `user` where username like concat('%', #{username}, '%') or name like concat('%', #{name}, '%') order by id desc")
    List<User> selectByLike(@Param("username")String username, @Param("name")String name);
    @Select("select * from `user` where username = #{username} order by id desc")
    User selectByUsername(String username);


//    @Delete("delete from `user` where id in #{id}")
//    void batchDelete(List<Integer> ids);

}
