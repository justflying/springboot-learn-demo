package com.wanyu.springboot.learn.redis.demo.service;

import com.wanyu.springboot.learn.redis.demo.entity.User;

public interface IUserService {


    String getStringByLettuce(String key);

    void expireKeyMinutes(String key,int num);

    User selectById(String id);

    String getStringByRedisson(String key);

}
