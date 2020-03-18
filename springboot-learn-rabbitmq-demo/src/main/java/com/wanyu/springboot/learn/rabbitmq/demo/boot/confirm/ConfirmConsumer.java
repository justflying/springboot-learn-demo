package com.wanyu.springboot.learn.rabbitmq.demo.boot.confirm;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/*
 * @Description Please describe the role of this class.
 * @Author wan
 * @Date 2020/3/18 14:17
 * @Version 1.0
 */
@Component
@RabbitListener
public class ConfirmConsumer implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

    }
}
