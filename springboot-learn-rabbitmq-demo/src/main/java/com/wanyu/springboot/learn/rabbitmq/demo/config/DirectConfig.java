package com.wanyu.springboot.learn.rabbitmq.demo.config;

import com.wanyu.springboot.learn.rabbitmq.demo.util.ConstantUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectConfig {


    @Bean
    public Queue testDirectQueue(){
        return new Queue(ConstantUtil.DIRECT_QUEUE_NAME,true);
    }

    @Bean
    public DirectExchange testDirectExchange(){
        return new DirectExchange(ConstantUtil.DIRECT_EXCHANGE_NAME);
    }

    @Bean
    public Binding directBinding(){
        return BindingBuilder.bind(testDirectQueue()).to(testDirectExchange()).with(ConstantUtil.DIRECT_ROUTING_KEY);
    }
}
