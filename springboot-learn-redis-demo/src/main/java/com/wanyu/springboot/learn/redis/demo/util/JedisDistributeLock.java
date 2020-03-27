package com.wanyu.springboot.learn.redis.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/*
 * @Description Please describe the role of this class.
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
}
