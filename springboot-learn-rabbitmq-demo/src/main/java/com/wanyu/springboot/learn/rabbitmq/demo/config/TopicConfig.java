package com.wanyu.springboot.learn.rabbitmq.demo.config;

import com.wanyu.springboot.learn.rabbitmq.demo.util.ConstantUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {

    @Bean
    public Queue queueA(){
        return new Queue(ConstantUtil.TOPIC_QUEUE_A);
    }

    @Bean
    @Qualifier(value = "queueB")
    public Queue queueB(){
        return new Queue(ConstantUtil.TOPIC_QUEUE_B);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(ConstantUtil.TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingExchangeA(){
        return BindingBuilder.bind(queueA()).to(topicExchange()).with(ConstantUtil.TOPIC_ROUTING_KEY_A);
    }

    @Bean
    public Binding bindingExchangeB(){
        return BindingBuilder.bind(queueB()).to(topicExchange()).with(ConstantUtil.TOPIC_ROUTING_KEY_B);
    }
}
