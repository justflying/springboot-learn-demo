package com.wanyu.springboot.learn.rabbitmq.demo.config;

import com.wanyu.springboot.learn.rabbitmq.demo.util.ConstantUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {

    @Bean
    public Queue queueA(){
        return new Queue(ConstantUtil.FANOUT_QUEUE_A);
    }

    @Bean
    @Qualifier(value = "queueB")
    public Queue queueB(){
        return new Queue(ConstantUtil.FANOUT_QUEUE_B);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(ConstantUtil.FANOUT_EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingExchangeA(){
        return BindingBuilder.bind(queueA()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingExchangeB(){
        return BindingBuilder.bind(queueB()).to(fanoutExchange());
    }
}
