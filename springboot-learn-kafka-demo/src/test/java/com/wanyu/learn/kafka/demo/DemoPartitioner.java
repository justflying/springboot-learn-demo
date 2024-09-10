package com.wanyu.springboot.learn.kafka.demo;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @name: Demopartitioner
 * @description: Demopartitioner
 * @version: V1.0.0.1
 * @author: Wan
 * @date: 2024-03-19
 */
public class DemoPartitioner implements Partitioner {

    // 自定义累加器
    private final AtomicInteger counter  = new AtomicInteger(0);

    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {

        List<PartitionInfo> partitions = cluster.partitionsForTopic(s);
        int partitionSize = partitions.size();
        if(o == bytes){
            // 如果为空，进行轮训分配到每一个分区
            return counter.getAndIncrement() % partitionSize;
        }
        else {
            // 不为空则进行hash运算
            return Utils.toPositive(Utils.murmur2(bytes) % partitionSize);
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
