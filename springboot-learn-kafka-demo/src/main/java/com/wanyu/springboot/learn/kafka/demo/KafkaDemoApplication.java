package com.wanyu.springboot.learn.kafka.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * @author wan
 * @since 2020-12-4 11:51:19
 * @version 1.0
 */
@SpringBootApplication
@EnableKafka
public class KafkaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaDemoApplication.class, args);
    }

}
