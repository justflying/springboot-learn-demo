package com.wanyu.elasticsearch.demo.common;

import lombok.Data;

@Data
public class CommonResult<T> {

    private Integer code;

    private T data;

    private String desc;

    public CommonResult(Integer code,T t,String desc){
        this.code = code;
        this.data = t;
        this.desc = desc;
    }

    public static <T> CommonResult success(T t){
        return new CommonResult<>(200,t,"操作成功");
    }
}
