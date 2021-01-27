package com.wanyu.rocketmq.test;

import com.wanyu.rocketmq.demo.RocketMQDemoApplication;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wan
 * @version 1.0
 * @date 2021/1/8 9:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RocketMQDemoApplication.class})
public class ProducerTest {


    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void testSendMessage(){
        rocketMQTemplate.convertAndSend("springboot-rocketmq","Hello Springboot RocketMQ");
    }

}
