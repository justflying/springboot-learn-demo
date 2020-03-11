package com.wanyu.springboot.learn.rabbitmq.demo.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {


    public static Connection getConnection() throws Exception{

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(ConstantUtil.HOST);
        connectionFactory.setPort(ConstantUtil.PORT);
        connectionFactory.setVirtualHost(ConstantUtil.VIRTUAL_HOST);
        connectionFactory.setUsername(ConstantUtil.USERNAME);
        connectionFactory.setPassword(ConstantUtil.PASSWORD);
        return connectionFactory.newConnection();
    }
}
