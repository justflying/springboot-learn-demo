package com.wanyu.mybatis.plus.demo.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanyu.mybatis.plus.demo.entity.User;
import com.wanyu.mybatis.plus.demo.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class InAndNotInTests {

    @Resource
    private UserMapper userMapper;

    /**
     * 字段 IN (value.get(0), value.get(1), ...)
     * in(R column, Collection<?> value)
     * in(boolean condition, R column, Collection<?> value)
     * 字段 IN (v0, v1, ...)
     * in(R column, Object... values)
     * in(boolean condition, R column, Object... values)
     *
     * 字段 IN (value.get(0), value.get(1), ...)
     * notIn(R column, Collection<?> value)
     * notIn(boolean condition, R column, Collection<?> value)
     * 字段 NOT IN (v0, v1, ...)
     * notIn(R column, Object... values)
     * notIn(boolean condition, R column, Object... values)
     */

    @Test
    public void testInByCollections(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age", Arrays.asList(18,20,23));
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE age IN (18,20,23)
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testInByObjects(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age", 18,20,23);
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE age IN (18,20,23)
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testNotInByCollections(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.notIn("age", Arrays.asList(18,20,23));
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE age NOT IN (18,20,23)
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testNotInByObjects(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.notIn("age", 18,20,23);
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE age NOT IN (18,20,23)
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
}
