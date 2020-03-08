package com.wanyu.mybatis.plus.demo.lambda;


import com.wanyu.mybatis.plus.demo.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 用来测试自定SQL，
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UpdateTests {


    @Resource
    private IUserService userService;


    @Test
    public void testLambda(){

    }
}
