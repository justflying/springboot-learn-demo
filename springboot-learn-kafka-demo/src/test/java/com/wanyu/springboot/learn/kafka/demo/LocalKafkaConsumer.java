package com.wanyu.springboot.learn.kafka.demo;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
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
            // 直接消费
         //   consumeMessage(consumer);

            // 根据分区消费
         //   consumeMessageByPartition(consumer);

            // 同步提交
            commitSync(consumer);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            consumer.close();
        }
    }

    private static void consumeMessage(KafkaConsumer<String, String> consumer) {
        ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));
        for (ConsumerRecord<String, String> record : records) {
            System.out.println("topic: " + record.topic() + ", partition : " + record.partition()
                + ", offset : " + record.offset());
            System.out.println(" key : " + record.key() + ", value : " + record.value());
        }
    }

    private static void consumeMessageByPartition(KafkaConsumer<String, String> consumer) {
        ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));
        for(TopicPartition tp : records.partitions()){
            for(ConsumerRecord<String, String> record : records.records(tp)){
                System.out.printf("partition:  " + record.partition() +  " value : "  + record.value());
            }
        }
    }

    private static void commitSync(KafkaConsumer<String, String> consumer){
        ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));

        for (ConsumerRecord<String, String> record : records) {
            System.out.println("topic: " + record.topic() + ", partition : " + record.partition()
                    + ", offset : " + record.offset());
        }
        consumer.commitSync();
    }

    private static void commitAsync(KafkaConsumer<String, String> consumer){
        ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));

        for (ConsumerRecord<String, String> record : records) {
            System.out.println("topic: " + record.topic() + ", partition : " + record.partition()
                    + ", offset : " + record.offset());
        }

        consumer.commitAsync((map, e) -> {
            if(e == null){
                System.out.println("异步提交成功");
            }else {
                System.out.printf("异步提交失败");
            }
        });
    }

}
