package com.wanyu.springboot.learn.redis.demo.service.impl;

import com.wanyu.springboot.learn.redis.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 * @Description Please describe the role of this class.
 * @Author wan
 * @Date 2020/3/20 14:41
 * @Version 1.0
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public String getString(String key){
        if(Optional.ofNullable(redisTemplate.hasKey(key)).orElseGet(() -> false)){
             return (String) redisTemplate.opsForValue().get(key);
        }else{
            String value = "学习Lettuce";
            redisTemplate.opsForValue().set(key,value);
            return value;
        }
    }
}
