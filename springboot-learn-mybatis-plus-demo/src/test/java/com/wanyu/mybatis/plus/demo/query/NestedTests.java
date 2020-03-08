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
public class NestedTests {

    @Resource
    private UserMapper userMapper;

    /**
     * 正常嵌套 不带 AND 或者 OR
     * nested(Consumer<Param> consumer)
     * nested(boolean condition, Consumer<Param> consumer)
     */
    @Test
    public void testNested(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",null).nested(i->i.eq("name","王天风").eq("age",30));
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE email = ? AND ( name = ? AND age = ? )
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testAnd(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",null).and(i->i.eq("name","王天风").eq("age",30));
        // SELECT id,name AS realName,age,email,manager_id,create_time FROM user WHERE email = ? AND ( name = ? AND age = ? )
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
}
