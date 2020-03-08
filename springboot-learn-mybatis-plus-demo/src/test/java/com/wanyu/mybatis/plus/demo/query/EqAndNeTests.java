package com.wanyu.mybatis.plus.demo.query;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanyu.mybatis.plus.demo.entity.User;
import com.wanyu.mybatis.plus.demo.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EqAndNeTests {

    @Resource
    private UserMapper userMapper;

    /**
     * eq 等于 =
     * 1.eq(R column, Object val)
     * 2.eq(boolean condition, R column, Object val)
     * ne 不等于 <>
     * ne(R column, Object val)
     * ne(boolean condition, R column, Object val)
     */
    @Test
    public void testEq1(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name","王天风");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE name = ?
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testEq2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(false,"name","王天风");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testNe1(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("name","王天风");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE name <> ?
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testNe2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne(false,"name","王天风");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
}
