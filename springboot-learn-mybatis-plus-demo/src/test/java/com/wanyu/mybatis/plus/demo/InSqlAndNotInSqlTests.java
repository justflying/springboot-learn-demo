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
public class InSqlAndNotInSqlTests {

    @Resource
    private UserMapper userMapper;

    /**
     * 字段 IN ( sql语句 )
     * inSql(R column, String inValue)
     * inSql(boolean condition, R column, String inValue)
     * 例: inSql("age", "1,2,3,4,5,6")--->age in (1,2,3,4,5,6)
     * 例: inSql("id", "select id from table where id < 3")--->id in (select id from table where id < 3)
     *
     * 字段 NOT IN ( sql语句 )
     * notInSql(R column, String inValue)
     * notInSql(boolean condition, R column, String inValue)
     *
     */
    @Test
    public void testInSql1(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("age", "18,20,23");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE age IN (18,20,23)
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testInSql2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("age", "select age from user where age between 18 and 40");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user
        // WHERE age IN (select age from user where age between 18 and 40)
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testNotInSql1(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.notInSql("age", "18,20,23");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE age NOT IN (18,20,23)
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testNotInSql2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.notInSql("age", "select age from user where age between 18 and 40");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user
        // WHERE age NOT IN (select age from user where age between 18 and 40)
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

}
