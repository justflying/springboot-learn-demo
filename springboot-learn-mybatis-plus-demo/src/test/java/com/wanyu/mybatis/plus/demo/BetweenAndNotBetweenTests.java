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
public class BetweenAndNotBetweenTests {


    @Resource
    private UserMapper userMapper;

    /**
     * BETWEEN 值1 AND 值2
     * between(R column, Object val1, Object val2)
     * between(boolean condition, R column, Object val1, Object val2)
     *
     * NOT BETWEEN 值1 AND 值2
     * notBetween(R column, Object val1, Object val2)
     * notBetween(boolean condition, R column, Object val1, Object val2)
     */
    @Test
    public void testBetween1(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("age",18,30);
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE age BETWEEN ? AND ?
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testBetween2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.between(false,"age",18,30);
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testNotBetween1(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.notBetween("age",18,30);
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE age NOT BETWEEN ? AND ?
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testNotBetween2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.notBetween(true,"age",18,30);
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE age NOT BETWEEN ? AND ?
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

}
