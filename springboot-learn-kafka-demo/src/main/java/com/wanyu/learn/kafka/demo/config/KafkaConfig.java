package com.wanyu.learn.kafka.demo.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaConfig {

    // 指定kafka broker地址
    public static final String brokerList = "localhost:9092";

    // 指定主题
    public static final String topic = "topic-demo";

    // 分组id
    public static final String groupId = "group.demo";

    public static Properties getProducerProperties(){
        Properties properties = new Properties();
        // 存储Key序列化方式
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        // 存储Value序列化方式
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        // 存储broker地址
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerList);
        return properties;
    }

    public static Properties getConsumerProperties(){
        Properties properties = new Properties();
        // 存储Key反序列化方式
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        // 存储Value反序列化方式
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        // 存储broker地址
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerList);

        // 指定消费组Id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        return properties;
    }
}
