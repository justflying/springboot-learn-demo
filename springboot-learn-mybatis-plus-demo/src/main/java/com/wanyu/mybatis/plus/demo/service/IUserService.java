package com.wanyu.mybatis.plus.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanyu.mybatis.plus.demo.entity.User;

import java.util.List;

public interface IUserService {

    List<User> getAllUser();

    IPage<User> selectPageByCondition();
}
