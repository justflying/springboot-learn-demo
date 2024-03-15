package com.wanyu.springboot.learn.kafka.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @name: KafkaProducer
 * @description: KafkaProducer
 * @version: V1.0.0.1
 * @author: Wan
 * @date: 2024-03-14
 */
public class LocalKafkaProducer {


    public static void main(String[] args) {
        Properties properties = KafkaConfig.producerSimpleConfig();
        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(properties);
        producer.send(buildMessage());
    }

    public static ProducerRecord<String,String> buildMessage(){
        ProducerRecord<String, String> record = new ProducerRecord<>(KafkaConfig.TOPIC, "hello kafka");
        return record;
    }
}
