package com.wanyu.springboot.learn.rabbitmq.demo.workfair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.wanyu.springboot.learn.rabbitmq.demo.util.ConnectionUtil;

import java.nio.charset.StandardCharsets;

public class ConsumerOne {

    private static final String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws Exception{
        // 1. 获取connection
        Connection connection = ConnectionUtil.getConnection();

        // 2. 获取channel
        Channel channel = connection.createChannel();

        // 3. 创建一个Queue  这里并没有把我们要做测试的消息持久化（存到磁盘）
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        channel.basicQos(1); // 保证一次只分发一个

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");


        // 旧的方式获取消息
//        Consumer consumer = new DefaultConsumer(channel){
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
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println(" ConsumerOne is Done ");
                // 应答消费完毕
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> {});
    }

}
