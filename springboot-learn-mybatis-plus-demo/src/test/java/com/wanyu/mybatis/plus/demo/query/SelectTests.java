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
public class SelectTests {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testSelect1(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("age","name");
        // SELECT age,name FROM user
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testSelect2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(User.class,i-> i.getProperty().startsWith("a"));
        // SELECT age,name FROM user
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
}
