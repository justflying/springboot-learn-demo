package com.wanyu.elasticsearch.demo.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

//@Configuration
public class ESConfig {

    @Bean
    public TransportClient client() throws Exception{
        TransportAddress address = new TransportAddress(InetAddress.getByName("192.168.211.133"),9300);
        Settings settings = Settings.builder().put("cluster.name","wanyu").build();
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(address);
        return client;
    }
}
