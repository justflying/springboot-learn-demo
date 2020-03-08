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
public class GroupByTests {

    @Resource
    private UserMapper userMapper;

    /**
     * 分组：GROUP BY 字段, ...
     * groupBy(R... columns)
     * groupBy(boolean condition, R... columns)
     */
    @Test
    public void testGroupBy(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.groupBy("age","id");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user GROUP BY age,id
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
}
