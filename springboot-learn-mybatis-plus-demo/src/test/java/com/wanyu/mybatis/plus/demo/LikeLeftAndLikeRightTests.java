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
public class LikeLeftAndLikeRightTests {

    @Resource
    private UserMapper userMapper;

    /**
     * LIKE '%值'
     * likeLeft(R column, Object val)
     * likeLeft(boolean condition, R column, Object val)
     *
     * LIKE '值%'
     * likeRight(R column, Object val)
     * likeRight(boolean condition, R column, Object val)
     */

    @Test
    public void testLikeLeft(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeLeft("name","王");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE name LIKE '%王'
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testLikeRight(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name","王");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE name LIKE 王%
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
}
