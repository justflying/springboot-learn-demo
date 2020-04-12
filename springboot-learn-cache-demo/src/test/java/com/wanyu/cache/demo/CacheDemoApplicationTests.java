package com.wanyu.cache.demo;

import com.wanyu.cache.demo.service.IEmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CacheDemoApplicationTests {

    @Autowired
    IEmployeeService employeeService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testRedisSetValue(){
        stringRedisTemplate.opsForValue().set("msg","hello redis");
    }

    @Test
    public void testRedisGetValue(){
        System.out.println(stringRedisTemplate.opsForValue().get("msg"));
    }

    @Test
    public void testRedisSetObj(){
        redisTemplate.opsForValue().set("emp01",employeeService.getEmpById(1L));
    }

    @Test
    public void testRedisGetObj(){
        System.out.println(redisTemplate.opsForValue().get("emp01"));
    }
}
