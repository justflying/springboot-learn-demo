package com.wanyu.mybatis.plus.demo.config;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
// 此注解开启以后，MybatisPlusDemoApplication上面的这个注解就可以注释，防止二次扫入
@MapperScan("com.wanyu.mybatis.plus.demo.mapper")
@EnableTransactionManagement
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
