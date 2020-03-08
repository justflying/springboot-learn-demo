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
public class LikeAndNotLikeTests {


    @Resource
    private UserMapper userMapper;


    /**
     * LIKE '%值%'
     * like(R column, Object val)
     * like(boolean condition, R column, Object val)
     *
     * NOT LIKE '%值%'
     * notLike(R column, Object val)
     * notLike(boolean condition, R column, Object val)
     */
    @Test
    public void testLike1(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","风");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE name LIKE ?
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testLike2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(false,"name","风");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    @Test
    public void testNotLike1(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.notLike("name","风");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE name NOT LIKE ?
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testNotLike2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.notLike(true,"name","风");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE name NOT LIKE ?
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
}
