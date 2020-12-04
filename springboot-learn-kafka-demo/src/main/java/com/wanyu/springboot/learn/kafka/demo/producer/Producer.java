package com.wanyu.springboot.learn.kafka.demo.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * @Description Please describe the role of this class.
 * @Author wan
 * @Date 2020/12/4 11:13
 * @Version 1.0
 */
@Component
@Slf4j
public class Producer {

    @Resource
    private KafkaTemplate<String,String> kafkaTemplate;

    public String producer(String topic,String message){
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, message);
        try {
            SendResult<String, String> stringStringSendResult = send.get();
            return stringStringSendResult.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            kafkaTemplate.flush();
        }
        return null;
    }
}
