package com.wanyu.springboot.learn.redis.demo.service.impl;

import com.wanyu.springboot.learn.redis.demo.entity.User;
import com.wanyu.springboot.learn.redis.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public String getString(String key){
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
}
