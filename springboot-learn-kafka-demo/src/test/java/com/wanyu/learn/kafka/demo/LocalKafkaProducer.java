package com.wanyu.springboot.learn.kafka.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

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
        // 发后即忘测试
        // fireAndForget(producer);
        // 同步发送测试
         syncSend(producer);
        // 异步发送
        // aSyncSend(producer);
    }

    /**
     * 构造单条消息
     * @author: Wan
     * @date: 2024-03-18
     *
     * @return ProducerRecord<String,String>
     */
    public static ProducerRecord<String,String> buildSingleMessage(){
        ProducerRecord<String, String> record = new ProducerRecord<>(KafkaConfig.TOPIC, "hello kafka");
        return record;
    }


    /**
     * 发后即忘，不管是否发送成功，效率最高，可靠性最低
     * @author: Wan
     * @date: 2024-03-15 
     * @param producer
     */
    public static void fireAndForget(KafkaProducer<String,String> producer){
        producer.send(buildSingleMessage());
    }
    
    /**
     * 同步发送
     * @author: Wan
     * @date: 2024-03-15
     * @param producer
     * @return 无
     */
    public static void syncSend(KafkaProducer<String,String> producer){

        try{
            Future<RecordMetadata> send = producer.send(buildSingleMessage());
            RecordMetadata recordMetadata = send.get();

            System.out.println("发送成功，存储到broker的topic ：" +recordMetadata.topic() + " partition: "
                    + recordMetadata.partition() + " offset: " + recordMetadata.offset());
        }catch (Exception e){
            e.printStackTrace();
            // 这里可以进行再次发送或者其他逻辑代码
        }

    }

    /**
     * 异步发送
     * @author: Wan
     * @date: 2024-03-18
     * @param producer
     * @return 无
     */
    public static void aSyncSend(KafkaProducer<String,String> producer){

        producer.send(buildSingleMessage(), (recordMetadata, e) -> {
            if(e != null){
                e.printStackTrace();
            }else {
                System.out.println("发送成功，存储到broker的topic ：" +recordMetadata.topic() + " partition: "
                        + recordMetadata.partition() + " offset: " + recordMetadata.offset());
            }
        });
    }

}
