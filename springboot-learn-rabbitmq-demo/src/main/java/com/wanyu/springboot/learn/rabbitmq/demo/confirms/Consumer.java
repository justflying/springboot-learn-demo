package com.wanyu.springboot.learn.rabbitmq.demo.confirms;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.wanyu.springboot.learn.rabbitmq.demo.util.ConnectionUtil;

import java.nio.charset.StandardCharsets;

public class Consumer {


    public static void main(String[] args) throws Exception {
        // 1. 获取connection
        Connection connection = ConnectionUtil.getConnection();

        // 2. 获取channel
        Channel channel = connection.createChannel();


        String queue = "test_confirm_individually_queue";
        channel.queueDeclare(queue,false,false,false,null);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");


        // 旧的方式获取消息
//        ConsumerOne consumer = new DefaultConsumer(channel){
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                super.handleDelivery(consumerTag, envelope, properties, body);
//                String msg = new String(body, StandardCharsets.UTF_8);
//                System.out.println("ConsumerOne Received: " +msg);
//                try {
//                    Thread.sleep(2000);
//                }catch (Exception ex){
//                    ex.printStackTrace();
//                }finally {
//                    System.out.println("ConsumerOne is done");
//                }
//            }
//        };
//        channel.basicConsume(QUEUE_NAME,true,consumer);

        // rabbitmq官网推荐方式
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" ConsumerOne Received: " + message);
            System.out.println(" ConsumerOne is Done");
        };
        channel.basicConsume(queue, true, deliverCallback, consumerTag -> {});
    }

}
