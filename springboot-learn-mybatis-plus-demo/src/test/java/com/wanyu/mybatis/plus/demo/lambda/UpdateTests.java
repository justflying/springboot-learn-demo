package com.wanyu.mybatis.plus.demo.lambda;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wanyu.mybatis.plus.demo.entity.User;
import com.wanyu.mybatis.plus.demo.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UpdateTests {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testLambda(){
        // UPDATE user SET age=?
        userMapper.update(new User(), Wrappers.<User>lambdaUpdate().set(User::getAge,18));
    }
}
