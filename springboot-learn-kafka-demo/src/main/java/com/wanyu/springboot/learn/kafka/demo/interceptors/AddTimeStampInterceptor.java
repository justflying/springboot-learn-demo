package com.wanyu.springboot.learn.kafka.demo.interceptors;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @name: AddTimeStampInterceptor
 * @description: AddTimeStampInterceptor
 * @version: V1.0.0.1
 * @author: Wan
 * @date: 2024-03-14
 */
public class AddTimeStampInterceptor implements ProducerInterceptor<String,String> {

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {

        String value = String.valueOf(System.currentTimeMillis()).concat("-").concat(record.value());
        ProducerRecord newRecord = new ProducerRecord(record.topic(),record.partition(),
                record.timestamp(),record.key(),value,record.headers());
        
        return newRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
