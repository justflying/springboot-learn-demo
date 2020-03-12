package com.wanyu.springboot.learn.rabbitmq.demo.workfair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wanyu.springboot.learn.rabbitmq.demo.util.ConnectionUtil;

import java.nio.charset.StandardCharsets;

public class Producer {


    private static final String QUEUE_NAME = "work_queue";


    public static void main(String[] args) throws Exception{
        // 1. 获取connection
        Connection connection = ConnectionUtil.getConnection();

        // 2. 获取channel
        Channel channel = connection.createChannel();

        // 3. 创建一个Queue  并持久化到消息队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);

        for (int i = 1; i < 51; i++) {
            String msg = "Hello RabbitMQ Work Fair" + " + " + i;
            // 3. 发布消息
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes(StandardCharsets.UTF_8));
            Thread.sleep( i * 20);
            System.out.println("生产者发送消息： "+ msg);
        }

        // 4. 关闭消息
        channel.close();
        connection.close();
    }
}
