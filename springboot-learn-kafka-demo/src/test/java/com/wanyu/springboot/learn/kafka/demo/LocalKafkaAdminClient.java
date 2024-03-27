package com.wanyu.springboot.learn.kafka.demo;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Collections;
import java.util.Properties;

/**
 * @name: LocalKafkaAdminClient
 * @description: LocalKafkaAdminClient
 * @version: V1.0.0.1
 * @author: Wan
 * @date: 2024-03-27
 */
public class LocalKafkaAdminClient {


    /**
     * 使用AdminClient创建topic
     */
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.BROKER_LIST);
        properties.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        AdminClient adminClient = AdminClient.create(properties);
        NewTopic topic = new NewTopic("topic-admin", 4,(short) 1);
        CreateTopicsResult result = adminClient.createTopics(Collections.singleton(topic));
        try{
            result.all().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        adminClient.close();
    }
}
