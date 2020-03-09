package com.wanyu.mybatis.plus.demo.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanyu.mybatis.plus.demo.entity.User;
import com.wanyu.mybatis.plus.demo.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PageTests {

    @Resource
    private IUserService userService;

    @Test
    public void testPage(){
        IPage<User> userIPage = userService.selectPageByCondition();
        userIPage.getRecords().forEach(System.out::println);
    }
}
