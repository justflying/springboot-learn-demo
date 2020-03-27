package com.wanyu.springboot.learn.redis.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/*
 * @Description Please describe the role of this class.
 * @Author wan
 * @Date 2020/3/23 11:37
 * @Version 1.0
 */
@Component
public class LettuceDistributeLock implements  RedisLock {


    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 加锁 set key value nx ex
     * @param lockKey 锁键
     * @param lockValue 锁值
     * @param expireTime 过期时间
     * @param timeUnit 实践单位
     * @return boolean 设置成功为true,设置失败为false
     */
    @Override
    public  boolean tryGetDistributeLock(String lockKey,
                                        String lockValue, Integer expireTime, TimeUnit timeUnit){
        return Optional.ofNullable(
                            redisTemplate.opsForValue().setIfAbsent(lockKey,lockValue,expireTime,timeUnit))
                       .orElse(false);
    }


    /**
     *  释放锁
     * @param lockKey 锁键
     * @return boolean
     */
    @Override
    public  boolean releaseDistributeLock(String lockKey,String lockValue){

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);

        return Optional.ofNullable(redisTemplate.execute(redisScript,
                Collections.singletonList(lockKey), lockValue)).orElse(0L) > 0;
    }
}
