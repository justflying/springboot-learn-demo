package com.wanyu.mybatis.plus.demo;


import com.wanyu.mybatis.plus.demo.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisPlusDemoApplicationTests {

    @Resource
    private UserMapper userMapper;

    /**
     * 测试查询所有数据
     */
    @Test
    public void testSelect(){
        System.out.println("=======select all method test======");
        userMapper.selectList(null).forEach(System.out::println);
    }

}
