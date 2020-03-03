package com.wanyu.mybatis.plus.demo;


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
public class GtAndGeTests {

    @Resource
    private UserMapper userMapper;

    /**
     * 大于 >
     * gt(R column, Object val)
     * gt(boolean condition, R column, Object val)
     *
     * 大于等于 >=
     * ge(R column, Object val)
     * ge(boolean condition, R column, Object val)
     *
     */

    @Test
    public void testGt1(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("age",18);
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE age > ?
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    @Test
    public void testGt2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt(false,"age",18);
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testGe1(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age",18);
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE age >= ?
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    @Test
    public void testGe2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge(false,"age",18);
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

}
