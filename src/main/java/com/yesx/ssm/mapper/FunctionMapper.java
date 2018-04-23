package com.yesx.ssm.mapper;

import com.yesx.ssm.po.Function;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper //声明是一个Mapper,与springbootApplication中的@MapperScan二选一写上即可
@Repository
public interface FunctionMapper {

    /**
     * 根据role_id查找功能
     * @param roleId
     * @return 实体数据
     */
    @ResultType(Function.class)
    List<Function> selectFunctionsByRoleId(int roleId);

    /**
     * 查找所有功能
     * @param
     * @return 实体数据
     */
    @ResultType(Function.class)
    List<Function> selectAllFunctions();
}
