package com.yesx.ssm.mapper;

import com.yesx.ssm.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper //声明是一个Mapper,与springbootApplication中的@MapperScan二选一写上即可
@Repository
public interface UserMapper {
    /**
     * 根据登录查询用户
     * @param loginName
     * @return 实体数据
     */
    @ResultType(User.class)
    User selectUser(String loginName);
}
