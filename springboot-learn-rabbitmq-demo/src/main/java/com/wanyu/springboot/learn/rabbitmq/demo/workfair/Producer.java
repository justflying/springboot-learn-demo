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

        // 3. 创建一个Queue  这里并没有把我们要做测试的消息持久化（存到磁盘）
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        for (int i = 0; i < 50; i++) {
            String msg = "Hello RabbitMQ " + " + " + i;
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
