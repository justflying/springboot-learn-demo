package com.wanyu.springboot.learn.rabbitmq.demo.simple;

import com.rabbitmq.client.*;
import com.wanyu.springboot.learn.rabbitmq.demo.util.ConnectionUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Consumer {

    private static final String QUEUE_NAME = "simple_queue";


    public static void main(String[] args) throws Exception{
        // 1. 创建连接
        Connection connection = ConnectionUtil.getConnection();

        // 2. 创建一个Channel
        Channel channel = connection.createChannel();

        // 3. 声明（创建）一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // 旧的方式获取消息
//        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel){
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                super.handleDelivery(consumerTag, envelope, properties, body);
//                String msg = new String(body, StandardCharsets.UTF_8);
//                System.out.println("ConsumerOne Received: " +msg);
//            }
//        };
//        channel.basicConsume(QUEUE_NAME,true,consumer);

        // rabbitmq 官方的demo使用代码
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
    }
}
