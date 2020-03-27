package com.wanyu.springboot.learn.redis.demo.service;

public interface IUserService {

    String getStringByLettuce(String key);

    String getStringByRedisson(String key);

    String getStringByJedis(String key);

}
