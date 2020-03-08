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
public class ApplyTests {

    @Resource
    private UserMapper userMapper;

    /**
     * 拼接 sql
     * apply(String applySql, Object... params)
     * apply(boolean condition, String applySql, Object... params)
     */

    @Test
    public void testApply(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("name ='王天风'");
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE name ='王天风'
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

}
