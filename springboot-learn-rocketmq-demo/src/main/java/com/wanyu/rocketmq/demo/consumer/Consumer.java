package com.wanyu.rocketmq.demo.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author wan
 * @version 1.0
 * @date 2021/1/27 10:34
 */
@Component
@RocketMQMessageListener(consumerGroup = "consumer-group",topic = "springboot-rocketmq")
public class Consumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.println(" 接收的消息是： " +message);
    }
}
