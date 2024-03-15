package com.wanyu.springboot.learn.kafka.demo;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 用来存储一些Kafka的基础配置
 *
 * @name: KafkaConfig
 * @description: KafkaConfig
 * @version: V1.0.0.1
 * @author: Wan
 * @date: 2024-03-14
 */
public class KafkaConfig {

    public static final String TOPIC = "test";

    private static  final String BROKER_LIST = "127.0.0.1:9092";

    public static Properties producerSimpleConfig(){

        Properties properties = new Properties();
        // 设置broker地址
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
        // 设置key和value的序列化器
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        return properties;
    }
}
