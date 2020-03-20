package com.wanyu.springboot.learn.redis.demo.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
 * @Description Please describe the role of this class.
 * @Author wan
 * @Date 2020/3/20 10:55
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
public class User implements Serializable {

    private String id;

    private String name;

    private Integer age;

    private LocalDateTime createTime;
}
