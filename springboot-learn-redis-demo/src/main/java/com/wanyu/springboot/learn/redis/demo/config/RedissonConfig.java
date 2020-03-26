package com.wanyu.springboot.learn.redis.demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * @Description Please describe the role of this class.
 * @Author wan
 * @Date 2020/3/26 10:24
 * @Version 1.0
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private int database;

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        String redisUrl  = String.format("redis://%s:%s",host,port);
        // 演示单机模式
        config.useSingleServer().setAddress(redisUrl).setPassword(password);
        config.useSingleServer().setDatabase(database);
        return Redisson.create(config);
    }
}
