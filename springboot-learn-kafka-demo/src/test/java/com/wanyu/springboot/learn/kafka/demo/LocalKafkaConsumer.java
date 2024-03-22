package com.wanyu.springboot.learn.kafka.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @name: LocalKafkaConsumer
 * @description: LocalKafkaConsumer
 * @version: V1.0.0.1
 * @author: Wan
 * @date: 2024-03-20
 */
public class LocalKafkaConsumer {


    public static final AtomicBoolean isRunning = new AtomicBoolean(true);

    public static void main(String[] args) {
        Properties properties = KafkaConfig.consumerSimpleConfig();
        KafkaConsumer<String,String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(KafkaConfig.TOPIC));
        try{

            ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("topic: " + record.topic() + ", partition : " + record.partition()
                    + ", offset : " + record.offset());
                System.out.println(" key : " + record.key() + ", value : " + record.value());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            consumer.close();
        }
    }


}
