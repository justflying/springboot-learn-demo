package com.wanyu.learn.kafka.demo.controller;

import com.wanyu.learn.kafka.demo.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description Please describe the role of this class.
 * @Author wan
 * @Date 2020/12/4 11:37
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/message")
public class KafkaController {

    @Autowired
    private Producer producer;

    @GetMapping(value = "/to-kafka/{message}")
    public String messageToKafka(@PathVariable String message){
        return producer.producer("test",message);
    }
}
