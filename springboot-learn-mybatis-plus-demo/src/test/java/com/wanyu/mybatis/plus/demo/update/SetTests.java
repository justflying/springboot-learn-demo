package com.wanyu.mybatis.plus.demo.update;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wanyu.mybatis.plus.demo.entity.User;
import com.wanyu.mybatis.plus.demo.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SetTests {

    @Resource
    private UserMapper userMapper;


    /**
     * SQL SET 字段
     * set(String column, Object val)
     * set(boolean condition, String column, Object val)
     */
    @Test
    public void testSet(){
        User user = new User();
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("name","张三").set("age",30).eq("id",6);
        // UPDATE user SET name=?,age=? WHERE id = ?
        int update = userMapper.update(user, updateWrapper);
        System.out.println("更新了"+ update+"项");
    }
}
