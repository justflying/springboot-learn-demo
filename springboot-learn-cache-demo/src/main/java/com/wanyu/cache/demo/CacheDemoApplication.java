package com.wanyu.cache.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 整合Redis作为缓存
 * 1. RedisCacheConfiguration 上面有个@ConditionalOnMissingBean(CacheManager.class)注解，
 *    事实上我们没有注册CacheManager进Spring IOC，容器会为我们自动注入一个RedisCacheManager
 *    这个时候，我们的缓存就自动的交给了Redis
 * 2. RedisCacheManager 就会注入一个RedisCache到我们的容器中，通过操作Redis作为缓存应用
 * 3. 默认保存的k-v 都是Object;利用序列化保存，如果保存为json
 *    a. 引入redis的starter,CacheManager会变为RedisCacheManager；
 *    b. 默认创建的RedisCacheManager 操作redis的时候使用的是RedisTemplate<Object,Object>
 *    c. RedisTemplate<Object,Object>默认使用jdk的序列化机制
 * 4. 自定义CacheManager
 *
 */
@SpringBootApplication
@MapperScan("com.wanyu.cache.demo.mapper")
@EnableCaching
public class CacheDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheDemoApplication.class, args);
    }

}
