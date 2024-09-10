package com.wanyu.learn.kafka.demo.producer;

import com.wanyu.learn.kafka.demo.config.KafkaConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class OldProducer {


    public static void main(String[] args) {

        // 获取配置
        Properties prop = KafkaConfig.getProducerProperties();
        // 创建kafka生产者客户端实例
        KafkaProducer<String,String> kafkaProducer = new KafkaProducer<>(prop);

        // 构建需要发送的消息
        ProducerRecord<String,String> record =
                new ProducerRecord<>(KafkaConfig.topic,"hello kafka");

        // 发送消息
        try{
            kafkaProducer.send(record);
        }catch (Exception e){
            e.printStackTrace();
        }

        // 发送完消息之后需要关闭客户端
        kafkaProducer.close();
    }
}
