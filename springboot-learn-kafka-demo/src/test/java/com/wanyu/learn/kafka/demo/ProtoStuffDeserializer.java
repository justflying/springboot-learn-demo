package com.wanyu.springboot.learn.kafka.demo;

import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

/**
 * @name: ProtoStuffDeserializer
 * @description: ProtoStuffDeserializer
 * @version: V1.0.0.1
 * @author: Wan
 * @date: 2024-03-21
 */
public class ProtoStuffDeserializer implements Deserializer<Company> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {}

    @Override
    public Company deserialize(String s, byte[] data) {
        if(data == null){
            return null;
        }
        Schema<Company> schema = RuntimeSchema.getSchema(Company.class);
        Company com = new Company();
        ProtobufIOUtil.mergeFrom(data, com, schema);
        return com;
    }

    @Override
    public void close() {}
}
