package com.wanyu.springboot.learn.redis.demo.util;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.*;

/*
 * @Description 使用Jedis实现分布式锁
 * @Author wan
 * @Date 2020/3/27 10:48
 * @Version 1.0
 */
@Component
public class JedisDistributeLock implements  RedisLock{


    @Autowired
    private JedisPool jedisPool;

    @Override
    public boolean tryGetDistributeLock(String lockKey, String lockValue,
                                        Integer expireTime, TimeUnit timeUnit) {
        Jedis jedis = jedisPool.getResource();
        SetParams setParams = new SetParams();
        setParams.nx().px(timeUnit.toMillis(expireTime));
        boolean ok = "OK".equals(jedis.set(lockKey, lockValue, setParams));
        jedis.close();
        return ok;
    }


    @Override
    public boolean releaseDistributeLock(String lockKey, String lockValue) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Jedis jedis = jedisPool.getResource();
        Object eval = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(lockValue));
        jedis.close();
        return eval.equals(1);
    }

    /**
     * 设计思路：
     *      1.如果业务操作超过一开始设置的redis锁时间，防止死锁(比如服务宕机，导致没有执行释放锁代码)
     *      2.但业务代码执行时间过久，可能会导致锁已经过期，其他线程也获得了锁，导致分布式锁功能失败
     *        采用定时任务定期延长锁的功能，如果服务宕机，JVM控制的定时任务直接失败
     * @param lockKey key
     * @param lockValue value
     * @param expireTime 过期时间
     */
    @Override
    public ScheduledFuture<?> renewLock(String lockKey, String lockValue, Integer expireTime, TimeUnit timeUnit,ScheduledExecutorService scheduledExecutorService) {
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('expire',KEYS[1], tonumber(ARGV[2])) else return 0 end";
        Jedis jedis = jedisPool.getResource();
        long round = Math.round(expireTime / 2);
        return scheduledExecutorService.scheduleAtFixedRate(() ->
            jedis.eval(script, Collections.singletonList(lockKey), Arrays.asList(lockValue,
                    String.valueOf(round))), round, round, timeUnit);
    }
}
