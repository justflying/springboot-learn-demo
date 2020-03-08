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
public class AndTests {


    @Resource
    private UserMapper userMapper;

    /**
     * AND 嵌套
     * and(Consumer<Param> consumer)
     * and(boolean condition, Consumer<Param> consumer)
     */
        @Test
        public void testOr1(){
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.and(i->i.eq("name","王天风").ge("age",30));
            // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE ( name = ? AND age >= ? )
            List<User> users = userMapper.selectList(queryWrapper);
            users.forEach(System.out::println);
        }
}
