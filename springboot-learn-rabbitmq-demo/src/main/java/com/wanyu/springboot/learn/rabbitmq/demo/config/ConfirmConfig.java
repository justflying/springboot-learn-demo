package com.wanyu.springboot.learn.rabbitmq.demo.config;

import com.wanyu.springboot.learn.rabbitmq.demo.boot.confirm.ConfirmConsumer;
import com.wanyu.springboot.learn.rabbitmq.demo.util.ConstantUtil;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * @Description Confirm 并不是一种Exchange 类型，
 *              而是针对生产者以及消费者进行应答的模式统称，使用的还是direct，topic类型的exchange
 * @Author wan
 * @Date 2020/3/18 14:19
 * @Version 1.0
 */
@Configuration
public class ConfirmConfig {


    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private ConfirmConsumer confirmConsumer;

    @Bean
    public Queue confirmQueue(){
        return new Queue(ConstantUtil.CONFIRM_QUEUE);
    }

    @Bean
    public TopicExchange confirmExchange(){
        return new TopicExchange(ConstantUtil.CONFIRM_EXCHANGE_NAME);
    }

    @Bean
    public Binding confirmBinding(){
        return BindingBuilder.bind(confirmQueue())
                .to(confirmExchange()).with(ConstantUtil.CONFIRM_ROUTING_KEY);
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // RabbitMQ默认是自动确认，这里改为手动确认消息
        container.setQueues(confirmQueue());
        container.setMessageListener(confirmConsumer);
        return container;
    }

    @Bean
    public RabbitTemplate confirmRabbitTemplate(){

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("ConfirmCallback:     "+"相关数据："+correlationData);
                System.out.println("ConfirmCallback:     "+"确认情况："+ack);
                System.out.println("ConfirmCallback:     "+"原因："+cause);
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText,
                                        String exchange, String routingKey) {
                System.out.println("ReturnCallback:     "+"消息："+message);
                System.out.println("ReturnCallback:     "+"回应码："+replyCode);
                System.out.println("ReturnCallback:     "+"回应信息："+replyText);
                System.out.println("ReturnCallback:     "+"交换机："+exchange);
                System.out.println("ReturnCallback:     "+"路由键："+routingKey);
            }
        });
        return rabbitTemplate;
    }

}
