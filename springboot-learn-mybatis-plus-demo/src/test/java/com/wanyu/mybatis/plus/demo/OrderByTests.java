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
public class OrderByTests {

    @Resource
    private UserMapper userMapper;

    /**
     * 排序：ORDER BY 字段, ...
     * orderBy(boolean condition, boolean isAsc, R... columns)
     *
     * 排序：ORDER BY 字段, ... ASC
     * orderByAsc(R... columns)
     * orderByAsc(boolean condition, R... columns)
     *
     * 排序：ORDER BY 字段, ... DESC
     * orderByDesc(R... columns)
     * orderByDesc(boolean condition, R... columns)
     */

    @Test
    public void testOrderBy(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true,true,"id","name");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user ORDER BY id ASC , name ASC
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testOrderByAsc(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id","name");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user ORDER BY id ASC , name ASC
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testOrderByDesc(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id","name");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user ORDER BY id DESC , name DESC
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testOrderByDescAndOrderByAsc(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id").orderByAsc("name");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user ORDER BY id DESC , name ASC
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
}
