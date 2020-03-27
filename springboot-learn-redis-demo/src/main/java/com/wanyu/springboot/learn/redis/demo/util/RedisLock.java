package com.wanyu.springboot.learn.redis.demo.util;

import java.util.concurrent.TimeUnit;

/*
 * @Description 基于Redis实现的分布式锁基础接口，后续不论使用
 *              哪个客户端，都可以实现该接口，切换客户端的时候，就可以省事很多。
 * @Author wan
 * @Date 2020/3/27 10:46
 * @Version 1.0
 */
public interface RedisLock {


    boolean tryGetDistributeLock(String lockKey,String lockValue,
                                 Integer expireTime, TimeUnit timeUnit);

    boolean releaseDistributeLock(String lockKey,String lockValue);
}
