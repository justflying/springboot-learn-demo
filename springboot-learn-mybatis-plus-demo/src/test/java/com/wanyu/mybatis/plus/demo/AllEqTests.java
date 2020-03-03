package com.wanyu.mybatis.plus.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanyu.mybatis.plus.demo.entity.User;
import com.wanyu.mybatis.plus.demo.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AllEqTests {


    @Resource
    private UserMapper userMapper;

    /**
     * 1.测试allEq函数
     *  allEq(Map<R, V> params)
     *  allEq(Map<R, V> params, boolean null2IsNull)
     *  allEq(boolean condition, Map<R, V> params, boolean null2IsNull)
     *  allEq(BiPredicate<R, V> filter, Map<R, V> params)
     *  allEq(BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull)
     *  allEq(boolean condition, BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull)
     */
    @Test
    public void testAllEq1(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String,Object> params = new HashMap<>();
        params.put("name","王天风");
        params.put("email","test@qq.com");
        // allEq(Map<R, V> params)
        queryWrapper.allEq(params);
        /*
         * 此时生成的SQL
         * SELECT id,name AS realName,age,email,manager_id,create_time
         * FROM user WHERE name = ? AND email = ?
         */
        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);
    }

    @Test
    public void testAllEq2(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String,Object> params = new HashMap<>();
        params.put("name","王天风");
        params.put("email","test@qq.com");
        params.put("age",null);
        // allEq(Map<R, V> params, boolean null2IsNull)
        queryWrapper.allEq(params,true);
        /*
         * 此时生成的SQL 会发现，age传递的是null  所以SQL中会判断 is NULL
         * SELECT id,name AS realName,age,email,manager_id,create_time FROM user
         * WHERE name = ? AND email = ? AND age IS NULL
         */
        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);
    }

    @Test
    public void testAllEq3(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String,Object> params = new HashMap<>();
        params.put("name","王天风");
        params.put("email","test@qq.com");
        params.put("age",null);
        // allEq(boolean condition, Map<R, V> params, boolean null2IsNull)
        queryWrapper.allEq(false,params,true);
        /*
         * 此时生成的SQL 会发现，所有的条件都没了，相当于没设置条件
         * SELECT id,name AS realName,age,email,manager_id,create_time FROM user
         */
        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);
    }

    @Test
    public void testAllEq4(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String,Object> params = new HashMap<>();
        params.put("name","王天风");
        params.put("email","test@qq.com");
        params.put("age",null);
        // allEq(BiPredicate<R, V> filter, Map<R, V> params)
        queryWrapper.allEq((k,v)->k.contains("m"),params,true);
        /*
         * 此时生成的SQL 因为age 里面不包含'm' 所以age的条件被过滤了
         * SELECT id,name AS realName,age,email,manager_id,create_time FROM user
         * WHERE name = ? AND email = ?
         */
        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);
    }

    @Test
    public void testAllEq5(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String,Object> params = new HashMap<>();
        params.put("name","王天风");
        params.put("email","test@qq.com");
        params.put("age",null);
        params.put("id",1L);
        // allEq(BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull)
        queryWrapper.allEq((k,v)->k.contains("a"),params,true);
        /*
         * 此时生成的SQL 因为id 里面不包含'a' 所以id的条件被过滤了,同时age对应null的被查出来
         * SELECT id,name AS realName,age,email,manager_id,create_time FROM user
         * WHERE name = ? AND email = ? AND age IS NULL
         */
        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);
    }

    @Test
    public void testAllEq6(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String,Object> params = new HashMap<>();
        params.put("name","王天风");
        params.put("email","test@qq.com");
        params.put("age",null);
        params.put("id",1L);
        // allEq(boolean condition, BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull)
        queryWrapper.allEq(true,(k,v)->k.contains("a"),params,true);
        /*
         * 此时生成的SQL 因为id 里面不包含'a' 所以id的条件被过滤了,同时age对应null的被查出来条件为true和testAllEq3完全不同
         * SELECT id,name AS realName,age,email,manager_id,create_time FROM user
         * WHERE name = ? AND email = ? AND age IS NULL
         */
        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);
    }
}
