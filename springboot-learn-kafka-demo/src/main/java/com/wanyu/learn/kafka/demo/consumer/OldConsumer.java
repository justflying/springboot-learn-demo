package com.wanyu.learn.kafka.demo.consumer;

import com.wanyu.learn.kafka.demo.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class OldConsumer {


    public static void main(String[] args) {

        // 获取配置信息
        Properties properties = KafkaConfig.getConsumerProperties();

        // 创建kafka消费者实例
        KafkaConsumer<String,String> consumer = new KafkaConsumer<>(properties);

        // 订阅主题
        consumer.subscribe(Collections.singletonList(KafkaConfig.topic));

        // 循环消费消息
        while(true){
            ConsumerRecords<String,String> records =
                    consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.value());
            }
        }

    }
}
