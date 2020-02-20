package com.wanyu.elasticsearch.demo.common;


public enum ErrorCode {

    FIND_SUCCESS(2001,"查询成功"),
    FIND_FAILED(2002,"查询失败");

    private Integer code;

    private String desc;


    ErrorCode(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode(){
        return this.code;
    }

    public String getDesc(){
        return this.desc;
    }

}
