package com.wanyu.springboot.learn.redis.demo;

import com.wanyu.springboot.learn.redis.demo.service.UserService;
import com.wanyu.springboot.learn.redis.demo.util.LettuceDistributeLock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class RedisDemoApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private LettuceDistributeLock redisLock;

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


    @Test
    void testLock(){
        System.out.println(redisLock.tryGetDistributeLock("lockKey", "lockValue",
                100, TimeUnit.SECONDS));
    }

    @Test
    void testUnLock1(){
        System.out.println(redisLock.releaseDistributeLock("lockKey","lockValue"));
    }

    @Test
    void testUnLock2(){
        System.out.println(redisLock.releaseDistributeLock("lockKey"));
    }
}
