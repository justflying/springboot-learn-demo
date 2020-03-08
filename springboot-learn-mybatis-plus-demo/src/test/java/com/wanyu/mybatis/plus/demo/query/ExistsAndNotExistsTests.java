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
public class ExistsAndNotExistsTests {

    @Resource
    private UserMapper userMapper;

    /**
     * 拼接 EXISTS ( sql语句 )
     * exists(String existsSql)
     * exists(boolean condition, String existsSql)
     *
     * 拼接 NOT EXISTS ( sql语句 )
     * notExists(String notExistsSql)
     * notExists(boolean condition, String notExistsSql)
     */

    @Test
    public void testExists(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.exists("select id from user where age = 1");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE EXISTS (select id from user where age = 1)
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testNotExists(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.notExists("select id from user where age = 1");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE NOT EXISTS (select id from user where age = 1)
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
}
