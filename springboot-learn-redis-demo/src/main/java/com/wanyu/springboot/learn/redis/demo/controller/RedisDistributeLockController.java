package com.wanyu.springboot.learn.redis.demo.controller;

import com.wanyu.springboot.learn.redis.demo.util.RedisLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
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
    private RedisLock lettuceDistributeLock;

    @Autowired
    private RedisLock jedisDistributeLock;

    @Autowired
    private RedissonClient redissonClient;

    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @RequestMapping(value = "/lettuce-lock")
    public String lettuceLock(){
        String lockKey = "lockKey";
        String lockValue = UUID.randomUUID().toString();
        boolean lockResult = lettuceDistributeLock.tryGetDistributeLock(lockKey, lockValue,
                2, TimeUnit.MINUTES);
        if(lockResult){
            ScheduledFuture<?> scheduledFuture = lettuceDistributeLock.renewLock(lockKey, lockValue, 10,
                    TimeUnit.SECONDS, scheduledExecutorService);
            System.out.println("开始执行实际业务");
            // 停止定时任务的执行
            scheduledFuture.cancel(true);
            if(lettuceDistributeLock.releaseDistributeLock(lockKey, lockValue)){
                System.out.println("执行完业务以后，释放锁");
            }
            return "success";
        }
        return "over";
    }

    @RequestMapping(value = "/jedis-lock")
    public String jedisLock(){
        String lockKey = "lockKey";
        String lockValue = "lockValue";

        boolean lockResult = jedisDistributeLock.tryGetDistributeLock(lockKey, lockValue,
                10, TimeUnit.SECONDS);

        if(lockResult){
            ScheduledFuture<?> scheduledFuture = jedisDistributeLock.renewLock(lockKey, lockValue, 10, TimeUnit.SECONDS,
                    scheduledExecutorService);
            System.out.println("开始执行实际业务");
            // 停止定时任务的执行
            scheduledFuture.cancel(true);
            if(jedisDistributeLock.releaseDistributeLock(lockKey, lockValue)){
                System.out.println("执行完业务，释放锁");
            }

            return "success";
        }
        return "over";
    }

    @RequestMapping(value = "/redisson-lock")
    public String redissonLock(){
        String lockKey = "lockKey";
        RLock lock = redissonClient.getLock(lockKey);
        try{
            lock.lock();
            System.out.println("开始执行实际业务");
        }finally {
            lock.unlock();
        }
        return "over";
    }

}
