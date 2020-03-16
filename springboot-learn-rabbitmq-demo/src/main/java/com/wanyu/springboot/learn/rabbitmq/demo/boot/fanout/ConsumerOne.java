package com.wanyu.springboot.learn.rabbitmq.demo.boot.fanout;


import com.wanyu.springboot.learn.rabbitmq.demo.util.ConstantUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = ConstantUtil.FANOUT_QUEUE_A)
public class ConsumerOne {

    @RabbitHandler
    public void process(Map<String,String> map){
        System.out.println("Message From Queue A :" + map);
    }
}
