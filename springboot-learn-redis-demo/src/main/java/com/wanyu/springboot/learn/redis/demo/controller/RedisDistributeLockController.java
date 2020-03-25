package com.wanyu.springboot.learn.redis.demo.controller;

import com.wanyu.springboot.learn.redis.demo.util.LettuceDistributeLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/*
 * @Description Please describe the role of this class.
 * @Author wan
 * @Date 2020/3/25 11:01
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/test")
public class RedisDistributeLockController {


    @Autowired
    private LettuceDistributeLock redisLock;

    @RequestMapping(value = "/redis-lock")
    public String redisLock(){
        String lockKey = "lockKey";
        String lockValue = "lockValue";
        boolean lockResult = redisLock.tryGetDistributeLock(lockKey, lockValue,
                2, TimeUnit.MINUTES);
        if(lockResult){
            redisLock.releaseDistributeLock(lockKey,lockValue);
            System.out.println("设值成功");
            return "success";
        }
        return "over";
    }
}
