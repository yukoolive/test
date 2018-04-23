package com.yesx.ssm.service;

import com.yesx.ssm.mapper.UserMapper;
import com.yesx.ssm.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;
    @Override
    public User queryUserByLoginName(String loginName) {
        User user = userMapper.selectUser(loginName);
        return user;
    }
}
