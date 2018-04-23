package com.yesx.ssm.mapper;

import com.yesx.ssm.po.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.springframework.stereotype.Repository;

@Mapper //声明是一个Mapper,与springbootApplication中的@MapperScan二选一写上即可
@Repository
public interface RoleMapper {

    /**
     * 根据role_id查找角色
     * @param roleId
     * @return 实体数据
     */
    @ResultType(Role.class)
    Role selectRoleByRoleId(int roleId);
}
