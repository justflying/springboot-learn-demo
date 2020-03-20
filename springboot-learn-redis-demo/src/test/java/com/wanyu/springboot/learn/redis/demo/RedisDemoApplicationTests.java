package com.wanyu.springboot.learn.redis.demo;

import com.wanyu.springboot.learn.redis.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisDemoApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        System.out.println(userService.getString("redis"));
    }

    @Test
    void testExpire(){
        userService.expireKeyMinutes("redis",2);
    }

    @Test
    void getHash(){
        System.out.println(userService.selectById("1003"));
    }
}
