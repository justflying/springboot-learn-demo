package com.wanyu.springboot.learn.rabbitmq.demo.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wanyu.springboot.learn.rabbitmq.demo.util.ConnectionUtil;

import java.nio.charset.StandardCharsets;

public class Producer {


    private static final String EXCHANGE_NAME = "exchange_topic";

    private static final String EXCHANGE_TYPE = "topic";

    public static void main(String[] args) throws Exception{
        // 1. 获取connection
        Connection connection = ConnectionUtil.getConnection();

        // 2. 获取channel
        Channel channel = connection.createChannel();

        // 3. 生命一个Exchange
        channel.exchangeDeclare(EXCHANGE_NAME,EXCHANGE_TYPE);

        String msg = "Hello RabbitMQ Topic";

        // 4. 发布消息到交换机
        channel.basicPublish(EXCHANGE_NAME,"test.info.xxx",null,msg.getBytes(StandardCharsets.UTF_8));

        System.out.println("生产者发送消息： "+ msg);

        // 5. 关闭消息
        channel.close();
        connection.close();
    }
}
