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
public class LtAndLeTests {



    @Resource
    private UserMapper userMapper;

    /**
     * 小于 <
     * lt(R column, Object val)
     * lt(boolean condition, R column, Object val)
     *
     * 小于等于 <=
     * le(R column, Object val)
     * le(boolean condition, R column, Object val)
     */
    @Test
    public void testLt1(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("age",18);
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE age < ?
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testLt2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt(true,"age",18);
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE age < ?
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    @Test
    public void testLe1(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.le("age",18);
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE age <= ?
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testLe2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.le(false,"age",18);
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
}
