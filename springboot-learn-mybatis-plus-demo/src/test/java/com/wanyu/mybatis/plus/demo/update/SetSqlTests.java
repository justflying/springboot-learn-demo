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
public class SetSqlTests {

    @Resource
    private UserMapper userMapper;

    /**
     * setSql(String sql)
     */
    @Test
    public void testSetSql(){
        User user = new User();
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("name = '张三'").eq("id",6);
        // UPDATE user SET name = '张三' WHERE id = ?
        int update = userMapper.update(user, updateWrapper);
        System.out.println("更新了"+ update+"项");
    }
}
