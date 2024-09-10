package com.wanyu.springboot.learn.redis.demo.util;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/*
 * @Description 基于Redis实现的分布式锁基础接口，后续不论使用
 *              哪个客户端，都可以实现该接口，切换客户端的时候，就可以省事很多。
 * @Author wan
 * @Date 2020/3/27 10:46
 * @Version 1.0
 */
public interface RedisLock {


    /**
     * 尝试获取redis锁
     * @param lockKey key
     * @param lockValue value
      * @param expireTime 过期时间
     * @param timeUnit 时间单位
     * @return boolean 是否获得锁成功
     */
    boolean tryGetDistributeLock(String lockKey,String lockValue,
                                 Integer expireTime, TimeUnit timeUnit);

    /**
     * 释放redis分布式锁
     * @param lockKey key
     * @param lockValue value
     * @return boolean 是否释放成功
     */
    boolean releaseDistributeLock(String lockKey,String lockValue);

    /**
     * 进行锁续约
     * @param lockKey key
     * @param lockValue value
     * @param expireTime 过期时间
     * @param timeUnit 时间单位
     * @param scheduledExecutorService 定时线程池
     */
    ScheduledFuture<?> renewLock(String lockKey, String lockValue, Integer expireTime,
                              TimeUnit timeUnit, ScheduledExecutorService scheduledExecutorService);
}
