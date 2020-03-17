package com.wanyu.springboot.learn.rabbitmq.demo.boot.topic;

import com.wanyu.springboot.learn.rabbitmq.demo.util.ConstantUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/*
 * @Description Please describe the role of this class.
 * @Author wan
 * @Date 2020/3/17 17:23
 * @Version 1.0
 */
@Component
@RabbitListener(queues = ConstantUtil.TOPIC_QUEUE_A)
public class ConsumerOne {

    @RabbitHandler
    public void process(Map<String,String> map){
        System.out.println("Topic Queue A:"  + map);
    }
}
