package com.wanyu.springboot.learn.kafka.demo;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.internals.RecordAccumulator;

import java.util.Map;

/**
 * @name: PrefixProducerInterceptor
 * @description: PrefixProducerInterceptor
 * @version: V1.0.0.1
 * @author: Wan
 * @date: 2024-03-19
 */
public class PrefixProducerInterceptor implements ProducerInterceptor<String,String> {

    private volatile long sendSuccess = 0;
    private volatile long sendFailure = 0;

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        String modifiedValue = "prefix-" + record.value();
        return new ProducerRecord<>(record.topic(),record.partition(),
                record.timestamp(),record.key(),modifiedValue, record.headers());
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        if (e == null){
            sendSuccess++;
        }else{
            sendFailure++;
        }
    }

    @Override
    public void close() {
        double successRatio = (double) sendSuccess / (sendSuccess + sendFailure);
        System.out.println("发送成功率： " + String.format("%f", successRatio * 100 + "%"));
    }

    @Override
    public void configure(Map<String, ?> map) {

    }

}
