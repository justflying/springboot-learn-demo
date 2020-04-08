package com.wanyu.cache.demo.config;


import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;


@Configuration
public class CacheConfig {

    /**
     * 实现key生成策略
     * @return KeyGenerator
     */
    @Bean("myKeyGenerator")
    public KeyGenerator keyGenerator(){
        return (Object target, Method method, Object... params) ->
                target.getClass().getSimpleName() +"."+ method.getName() + "["+ Arrays.asList(params).toString() +"]";
    }
}
