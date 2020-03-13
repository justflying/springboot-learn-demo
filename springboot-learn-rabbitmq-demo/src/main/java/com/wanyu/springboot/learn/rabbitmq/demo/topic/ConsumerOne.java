package com.wanyu.springboot.learn.rabbitmq.demo.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.wanyu.springboot.learn.rabbitmq.demo.util.ConnectionUtil;

import java.nio.charset.StandardCharsets;

public class ConsumerOne {

    private static final String EXCHANGE_NAME = "exchange_topic";

    private static final String EXCHANGE_TYPE = "topic";


    public static void main(String[] args) throws Exception{
        // 1. 获取connection
        Connection connection = ConnectionUtil.getConnection();

        // 2. 获取channel
        Channel channel = connection.createChannel();

        // 3. 声明（创建）一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME,EXCHANGE_TYPE);

        // 4. 创建一个Queue
        String queue = channel.queueDeclare().getQueue();

        String key = "test.#";

        channel.queueBind(queue,EXCHANGE_NAME,key);

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
            System.out.println(" ConsumerOne is Done");
        };
        channel.basicConsume(queue, true, deliverCallback, consumerTag -> {});
    }

}
