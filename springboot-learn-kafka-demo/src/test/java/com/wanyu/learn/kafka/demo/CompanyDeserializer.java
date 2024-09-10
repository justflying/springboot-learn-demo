package com.wanyu.springboot.learn.kafka.demo;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import java.nio.ByteBuffer;
import java.util.Map;

/**
 * @name: CompanyDeserializer
 * @description: CompanyDeserializer
 * @version: V1.0.0.1
 * @author: Wan
 * @date: 2024-03-21
 */
public class CompanyDeserializer implements Deserializer<Company> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {}

    @Override
    public Company deserialize(String topic, byte[] data) {
        if(data == null) {
            return null;
        }
        if(data.length < 8){
            throw new SerializationException("Size of data received by Deserializer is shorter than expected !");
        }
        ByteBuffer buffer = ByteBuffer.wrap(data);
        int nameLen, addressLen;
        String name, address;
        nameLen = buffer.getInt();
        byte[] nameBytes = new byte[nameLen];
        buffer.get(nameBytes);
        addressLen = buffer.getInt();
        byte[] addressBytes = new byte[addressLen];
        buffer.get(addressBytes);
        try{
            name = new String(nameBytes, "UTF-8");
            address = new String(addressBytes, "UTF-8");
        }catch (Exception e){
            throw new SerializationException("Deserializer Exception!");
        }
        Company company = new Company();
        company.setName(name);
        company.setAddress(address);
        return company;
    }

    @Override
    public void close() {}
}
