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
public class HavingTests {

    @Resource
    private UserMapper userMapper;


    /**
     * HAVING ( sql语句 )
     * having(String sqlHaving, Object... params)
     */
    @Test
    public void testHaving(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("age").groupBy("age").having("count(age) > 1");
        // SELECT age FROM user GROUP BY age HAVING count(age) > 1
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
}
