package com.wanyu.mybatis.plus.demo.customize;

import com.wanyu.mybatis.plus.demo.entity.User;
import com.wanyu.mybatis.plus.demo.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomizeSQL {

    @Resource
    private IUserService userService;

    @Test
    public void testCustomSql(){
        // select * from user WHERE age = ?
        // 这里会存在一个问题，就是name 在User中别名是realName  因为使用的 select * 会无法自动装配
        List<User> all = userService.getAll();
        all.forEach(System.out::println);

    }
}
