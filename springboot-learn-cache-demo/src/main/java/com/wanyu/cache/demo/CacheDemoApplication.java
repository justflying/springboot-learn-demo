package com.wanyu.cache.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.wanyu.cache.demo.mapper")
@EnableCaching
public class CacheDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheDemoApplication.class, args);
    }

}
