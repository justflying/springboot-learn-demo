package com.wanyu.cache.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("employee")
public class Employee extends Model<Employee> {

    @TableId
    private Long id;

    private String lastName;

    private String email;

    private Integer gender;

    private Long dId;
}
