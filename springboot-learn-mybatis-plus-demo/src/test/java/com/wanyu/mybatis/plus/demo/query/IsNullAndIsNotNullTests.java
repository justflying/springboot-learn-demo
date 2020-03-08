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
public class IsNullAndIsNotNullTests {

    @Resource
    private UserMapper userMapper;

    /**
     * 字段 IS NULL
     * isNull(R column)
     * isNull(boolean condition, R column)
     *
     * 字段 IS NOT NULL
     * isNotNull(R column)
     * isNotNull(boolean condition, R column)
     */
    @Test
    public void testIsNull(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("email");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE email IS NULL
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testIsNotNull(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("email");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE email IS NOT NULL
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

}

