package com.wanyu.springboot.learn.kafka.demo.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Description Please describe the role of this class.
 * @Author wan
 * @Date 2020/12/4 11:30
 * @Version 1.0
 */
@Component
public class Consumer {

    @KafkaListener(topics = "springboot-kafka")
    public void listen(ConsumerRecord<String,String> record){
        System.out.println(record.topic() + record.partition()  + record.value());
    }
}
