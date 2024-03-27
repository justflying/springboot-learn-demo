package com.wanyu.springboot.learn.kafka.demo;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
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

    public static  final String BROKER_LIST = "127.0.0.1:9092";
    private static  final String GROUP_ID = "group.demo";
    private static  final String CLIENT_ID = "consumer.client.id.demo";


    public static Properties producerSimpleConfig(){

        Properties properties = new Properties();
        // 设置broker地址
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
        // 设置key和value的序列化器
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        // 设置分区器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, DemoPartitioner.class.getName());
        // 设置拦截器
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, PrefixProducerInterceptor.class.getName());
        return properties;
    }

    public static Properties consumerSimpleConfig(){
        Properties properties = new Properties();
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, CLIENT_ID);
        return properties;
    }

    public static Properties consumerCompleteConfig(){
        Properties properties = new Properties();
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, CLIENT_ID);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return properties;
    }
}
