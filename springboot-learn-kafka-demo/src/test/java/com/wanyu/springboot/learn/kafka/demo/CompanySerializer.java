package com.wanyu.springboot.learn.kafka.demo;

import org.apache.kafka.common.serialization.Serializer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @name: CompanySerializer
 * @description: CompanySerializer
 * @version: V1.0.0.1
 * @author: Wan
 * @date: 2024-03-18
 */
public class CompanySerializer implements Serializer<Company> {


    @Override
    public void configure(Map<String, ?> map, boolean b) {}

    @Override
    public byte[] serialize(String s, Company company) {
        if(company == null){
            return null;
        }
        byte[] name, address;
        try{
            if(company.getName() != null){
                name = company.getName().getBytes(Charset.forName("UTF-8"));
            }else{
                name = new byte[0];
            }
            if(company.getAddress() != null){
                address = company.getAddress().getBytes(Charset.forName("UTF-8"));
            }else{
                address = new byte[0];
            }
            ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + name.length + address.length);
            buffer.putInt(name.length);
            buffer.put(name);
            buffer.putInt(address.length);
            buffer.put(address);
            return buffer.array();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public void close() {}
}
