package com.wanyu.springboot.learn.rabbitmq.demo.util;


public class ConstantUtil {

    public static final String HOST = "192.168.211.133";

    public static final Integer PORT = 5672;

    public static final String VIRTUAL_HOST = "/vhost_root";

    public static final String USERNAME = "root";

    public static final String PASSWORD = "myroot";


//    public static final String HOST = "120.78.131.194";
//
//    public static final Integer PORT = 5672;
//
//    public static final String VIRTUAL_HOST = "/learn_demo";
//
//    public static final String USERNAME = "user";
//
//    public static final String PASSWORD = "password";

    public static final String DIRECT_EXCHANGE_NAME = "boot_direct_exchange_name";

    public static final String DIRECT_QUEUE_NAME = "boot_direct_queue_name";

    public static final String DIRECT_ROUTING_KEY = "boot_direct_routing_key";


    public static final String FANOUT_EXCHANGE_NAME = "boot_fanout_exchange_name";

    public static final String FANOUT_QUEUE_A = "boot_fanout_queue_A";

    public static final String FANOUT_QUEUE_B = "boot_fanout_queue_B";


    public static final String TOPIC_EXCHANGE_NAME = "boot_topic_exchange_name";

    public static final String TOPIC_QUEUE_NAME = "boot_topic_queue_name";

    public static final String TOPIC_ROUTING_KEY = "boot_topic.#";
}
