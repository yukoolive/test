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

    /**
     * solr常用操作模板：包括创建索引，查询、删除索引
     * @return
     */
    public boolean solrOptDemo();

    /**
     * 基于实体类User创建索引
     * @return
     */
    public boolean setSolrUser(User user);

    /**
     * 基于loginName查找实体类
     * @param loginName
     * @return
     */
    public List<User> selectSolrByLoginName(String loginName);
}
