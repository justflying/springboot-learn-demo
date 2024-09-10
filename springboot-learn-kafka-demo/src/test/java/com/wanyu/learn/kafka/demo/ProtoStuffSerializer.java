package com.wanyu.springboot.learn.kafka.demo;


import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * @name: ProtoStuffSerializer
 * @description: ProtoStuffSerializer
 * @version: V1.0.0.1
 * @author: Wan
 * @date: 2024-03-21
 */
public class ProtoStuffSerializer implements Serializer<Company>{
    @Override
    public void configure(Map<String, ?> map, boolean b) {}

    @Override
    public byte[] serialize(String s, Company company) {
        if(company == null){
            return null;
        }
        Schema schema = (Schema)RuntimeSchema.getSchema(company.getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        byte[] protoStuff;
        try{
            protoStuff = ProtobufIOUtil.toByteArray(company, schema, buffer);
        }catch (Exception e){
            throw new IllegalStateException(e.getMessage(), e);
        }finally {
            buffer.clear();
        }
        return protoStuff;
    }

    @Override
    public void close() {}
}
