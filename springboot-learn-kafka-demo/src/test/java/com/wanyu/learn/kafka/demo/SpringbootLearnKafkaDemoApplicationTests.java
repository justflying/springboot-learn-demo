package com.wanyu.learn.kafka.demo;

import com.wanyu.springboot.learn.kafka.demo.producer.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringbootLearnKafkaDemoApplicationTests {



    @Autowired
    Producer producer;

    @Test
    public void testProduceMessage(){

        for (int i = 0; i < 200; i++) {
            System.out.println("start send message");
            producer.producer("test", "hello-kafka : " + i);
        }
    }
}
