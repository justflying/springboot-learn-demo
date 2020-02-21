package com.wanyu.mybatis.plus.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "user")
public class User extends Model<User> {

    @TableId
    private Long id;

    // 指定对应数据库的列名
    @TableField("name")
    private String realName;

    private Integer age;

    private String email;

    private Long managerId;

    private LocalDateTime createTime;

    // 排除不对应的字段
    @TableField(exist = false)
    private String remark;
}
