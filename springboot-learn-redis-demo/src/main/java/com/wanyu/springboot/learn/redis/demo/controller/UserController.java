package com.wanyu.springboot.learn.redis.demo.controller;

import com.wanyu.springboot.learn.redis.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @Description Please describe the role of this class.
 * @Author wan
 * @Date 2020/3/25 10:57
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/redis")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping(value = "/lettuce/{key}")
    public String testLettuce(@PathVariable("key") String key){
        return userService.getStringByLettuce(key);
    }


    @GetMapping(value = "/redisson/{key}")
    public String testRedisson(@PathVariable("key") String key){
        return userService.getStringByRedisson(key);
    }


    @GetMapping(value = "/jedis/{key}")
    public String getStringValue(@PathVariable("key") String key){
        return userService.getStringByJedis(key);
    }
}
