package com.wanyu.mybatis.plus.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanyu.mybatis.plus.demo.entity.User;
import com.wanyu.mybatis.plus.demo.mapper.UserMapper;
import com.wanyu.mybatis.plus.demo.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser(Wrappers.<User>lambdaQuery().eq(User::getAge,18));
    }

    @Override
    public IPage<User> selectPageByCondition() {
        Page<User> page = new Page<>(1,10);
        return userMapper.selectPageByAge(page, 18);
    }
}
