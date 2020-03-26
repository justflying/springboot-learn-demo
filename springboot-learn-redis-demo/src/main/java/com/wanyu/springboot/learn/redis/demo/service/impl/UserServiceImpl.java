package com.wanyu.springboot.learn.redis.demo.service.impl;

import com.wanyu.springboot.learn.redis.demo.entity.User;
import com.wanyu.springboot.learn.redis.demo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/*
 * @Description Please describe the role of this class.
 * @Author wan
 * @Date 2020/3/20 14:41
 * @Version 1.0
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private JedisPool jedisPool;

    public String getStringByLettuce(String key){
        if(Optional.ofNullable(redisTemplate.hasKey(key)).orElse( false)){
            log.info("从redis中查询出来的数据");
            return (String) redisTemplate.opsForValue().get(key);
        }else{
            log.info("从数据库中查出来的数据");
            String value = "学习Lettuce";
            redisTemplate.opsForValue().set(key,value);
            return value;
        }
    }

    @Override
    public void expireKeyMinutes(String key, int num) {
        if(Optional.ofNullable(redisTemplate.hasKey(key)).orElse( false)){
            redisTemplate.expire(key,num, TimeUnit.MINUTES);
        }
    }

    @Override
    public User selectById(String id) {
        if(Optional.ofNullable(redisTemplate.opsForHash().hasKey("user",id)).orElse( false)){
            log.info("从redis中查询出来的数据");
            return (User) redisTemplate.opsForHash().get("user",id);
        }else{
            log.info("从数据库中查出来的数据");
            User user = new User().setId(id).setName("张三").setAge(23).setCreateTime(LocalDateTime.now());
            redisTemplate.opsForHash().put("user",id, user);
            return user;
        }
    }

    @Override
    public String getStringByRedisson(String key) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        if(bucket.isExists()){
            log.info("从redis中查询出来的数据");
            return bucket.get().toString();
        }else{
            log.info("从数据库中查出来的数据");
            String value = "学习Redisson";
            bucket.set(value);
            return value;
        }
    }

    @Override
    public String getStringByJedis(String key) {
        Jedis jedis = jedisPool.getResource();
        try{
            if (jedis.exists(key)) {
                log.info("从redis中获取值");
                return jedis.get(key);
            }else{
                // 这里是模拟从数据库拿数据，真实应用的时候请把这段代码替换为操作数据库的代码
                log.info("从数据库中获取值");
                String value = "learn jedis";
                jedis.set(key,value);
                return value;
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally{
            jedis.close();
        }
        return null;
    }
}
