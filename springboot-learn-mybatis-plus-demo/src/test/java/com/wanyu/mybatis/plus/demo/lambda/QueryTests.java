package com.wanyu.mybatis.plus.demo.lambda;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
public class QueryTests {

    @Resource
    private UserMapper userMapper;

    /**
     * lambda使用起来很方便，字段方面无需多考虑
     *
     */
    @Test
    public void testLambda(){
        //SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE age = ?
        List<User> users = userMapper.selectList(Wrappers.<User>lambdaQuery()
                .eq(User::getAge, 18));
        users.forEach(System.out::println);
    }

}
