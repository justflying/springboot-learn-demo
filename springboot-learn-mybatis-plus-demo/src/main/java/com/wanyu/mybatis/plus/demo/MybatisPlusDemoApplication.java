package com.wanyu.mybatis.plus.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 因为添加了MybatisPlusConfig这个配置类，这里就可以注释了
//@MapperScan("com.wanyu.mybatis.plus.demo.mapper")
public class MybatisPlusDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusDemoApplication.class, args);
    }

}
