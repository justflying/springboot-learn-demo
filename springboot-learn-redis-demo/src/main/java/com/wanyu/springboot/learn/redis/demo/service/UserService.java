package com.wanyu.springboot.learn.redis.demo.service;

import com.wanyu.springboot.learn.redis.demo.entity.User;

public interface UserService {


    String getString(String key);

    void expireKeyMinutes(String key,int num);

    User selectById(String id);
}
