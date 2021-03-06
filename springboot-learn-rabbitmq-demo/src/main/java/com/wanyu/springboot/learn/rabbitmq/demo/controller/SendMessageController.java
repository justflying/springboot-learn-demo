package com.wanyu.springboot.learn.rabbitmq.demo.controller;

import com.wanyu.springboot.learn.rabbitmq.demo.util.ConstantUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/mq")
public class SendMessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier(value = "confirmRabbitTemplate")
    private RabbitTemplate confirmRabbitTemplate;

    @GetMapping(value = "/send-direct-test")
    public String sendDirectMessage(){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "Hello RabbitMQ Direct";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,String>  map = new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        rabbitTemplate.convertAndSend(ConstantUtil.DIRECT_EXCHANGE_NAME,ConstantUtil.DIRECT_ROUTING_KEY,map);
        return "ok";
    }

    @GetMapping(value = "/send-fanout-test")
    public String sendFanoutMessage(){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "Hello RabbitMQ Fanout";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,String>  map = new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        rabbitTemplate.convertAndSend(ConstantUtil.FANOUT_EXCHANGE_NAME,"",map);
        return "ok";
    }


    @GetMapping(value = "/send-topic-test-a")
    public String sendTopicMessageA(){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "Hello RabbitMQ Topic";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,String>  map = new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        rabbitTemplate.convertAndSend(ConstantUtil.TOPIC_EXCHANGE_NAME,
                "boot_topic.xxx.yyy",map);
        return "ok";
    }


    @GetMapping(value = "/send-topic-test-b")
    public String sendTopicMessageB(){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "Hello RabbitMQ Topic";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,String>  map = new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        rabbitTemplate.convertAndSend(ConstantUtil.TOPIC_EXCHANGE_NAME,"boot_topic.xxx",map);
        return "ok";
    }



    @GetMapping(value = "/send-confirm-test")
    public String sendConfirmTest(){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "Hello RabbitMQ Confirm";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,String>  map = new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        confirmRabbitTemplate.convertAndSend(ConstantUtil.CONFIRM_EXCHANGE_NAME,"boot_confirm.xxx",map);
        return "ok";
    }
}
