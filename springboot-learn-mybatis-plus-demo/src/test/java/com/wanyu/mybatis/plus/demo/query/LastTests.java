package com.wanyu.mybatis.plus.demo.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanyu.mybatis.plus.demo.entity.User;
import com.wanyu.mybatis.plus.demo.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LastTests {

    @Resource
    private UserMapper userMapper;

    /**
     * 无视优化规则直接拼接到 sql 的最后
     * last(String lastSql)
     * last(boolean condition, String lastSql)
     */

    @Test
    public void testLast(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("limit 1");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user limit 1
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

}
