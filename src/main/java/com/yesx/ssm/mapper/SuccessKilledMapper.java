package com.yesx.ssm.mapper;

import com.yesx.ssm.po.SuccessKilled;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.springframework.stereotype.Repository;

@Mapper //声明是一个Mapper,与springbootApplication中的@MapperScan二选一写上即可
@Repository
public interface SuccessKilledMapper {
    /**
     * 插入购买明细，可过滤重复
     * @param seckillId
     * @param userid
     * @return 插入的行数
     */
    int insertSuccessKilled(@Param("seckillId")long seckillId, @Param("userid")long userid);

    /**
     * 根据秒杀商品ID查询明细SuccessKilled对象， 携带了Seckill秒杀产品对象
     * @param seckillId
     * @param userid
     * @return SuccessKilled
     */
    @ResultType(SuccessKilled.class)
    SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId, @Param("userid")long userid);
}
