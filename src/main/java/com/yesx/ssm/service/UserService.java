package com.yesx.ssm.service;

import com.yesx.ssm.po.User;

import java.util.List;

public interface UserService {

    /**
     * 根据传入名称获取数据
     * @param loginName 名称
     * @return 实体类
     */
    public User queryUserByLoginName(String loginName);
}
