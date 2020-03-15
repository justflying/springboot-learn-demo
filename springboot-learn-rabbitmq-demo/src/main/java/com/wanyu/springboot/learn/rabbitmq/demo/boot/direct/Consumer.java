package com.wanyu.springboot.learn.rabbitmq.demo.boot.direct;


import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "direct_queue_name")
public class Consumer {

    @RabbitHandler
    public void process(Map<String,String> msg){
        System.out.println(msg.toString());
    }
}
