package com.yesx.ssm.service;

import com.yesx.ssm.dao.RedisDao;
import com.yesx.ssm.dto.Exposer;
import com.yesx.ssm.dto.SeckillExecution;
import com.yesx.ssm.enums.SeckillStatEnum;
import com.yesx.ssm.exception.RepeatKillException;
import com.yesx.ssm.exception.SeckillCloseException;
import com.yesx.ssm.exception.SeckillException;
import com.yesx.ssm.mapper.SeckillMapper;
import com.yesx.ssm.mapper.SuccessKilledMapper;
import com.yesx.ssm.po.Seckill;
import com.yesx.ssm.po.SuccessKilled;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SeckillServiceImpl implements SeckillService {
    //日志对象
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private SuccessKilledMapper successKilledMapper;

    @Autowired
    private RedisDao redisDao;

    //加入一个混淆字符串(秒杀接口)的salt，为了我避免用户猜出我们的md5值，值任意给，越复杂越好
    private final String salt = "sadjgioqwelrhaljflutoiu293480523*&%*&*#";

    public List<Seckill> getSeckillList() {
        return seckillMapper.queryAll(0, 4);
    }

    public Seckill getById(long seckillId) {
        return seckillMapper.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        //缓存优化
        //1。访问redis


        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null) {
            //2.访问数据库
            seckill = seckillMapper.queryById(seckillId);
            if (seckill == null) {//说明查不到这个秒杀产品的记录
                return new Exposer(false, seckillId);
            } else {
                //3,放入redis
                redisDao.putSeckill(seckill);
            }

        }
        Date startTime = seckill.getStart_time();
        Date endTime = seckill.getEnd_time();
        Date nowTime = new Date();
        //若是秒杀未开启
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }

        //秒杀开启，返回秒杀商品的id、用给接口加密的md5
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Transactional
    /**
     * 使用注解控制事务方法的优点:
     * 1.开发团队达成一致约定，明确标注事务方法的编程风格
     * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部
     * 3.不是所有的方法都需要事务，如只有一条修改操作、只读操作不要事务控制
     */
    public SeckillExecution executeSeckill(long seckillId, long userid, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        //执行秒杀逻辑：减库存+记录购买行为
        Date nowTime = new Date();
        try {
            //否则更新了库存，秒杀成功,增加明细
            int insertCount = successKilledMapper.insertSuccessKilled(seckillId, userid);
            //看是否该明细被重复插入，即用户是否重复秒杀
            if (insertCount <= 0) {
                throw new RepeatKillException("seckill repeated");
            } else {

                //减库存,热点商品竞争，update方法会拿到行级锁
                int updateCount = seckillMapper.reduceNumber(seckillId, nowTime);
                if (updateCount <= 0) {
                    //没有更新库存记录，说明秒杀结束 rollback
                    throw new SeckillCloseException("seckill is closed");
                } else {
                    //秒杀成功,得到成功插入的明细记录,并返回成功秒杀的信息 commit
                    SuccessKilled successKilled = successKilledMapper.queryByIdWithSeckill(seckillId, userid);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }

            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //所有编译器异常，转化成运行期异常
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }

    public SeckillExecution executeSeckillProcedure(long seckillId, long userid, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            return new SeckillExecution(seckillId, SeckillStatEnum.DATE_REWRITE);
        }
        Date time = new Date();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("seckillId", seckillId);
        map.put("userid", userid);
        map.put("killTime", time);
        map.put("result", null);
        try {
            seckillMapper.killByProcedure(map);
            int result = MapUtils.getInteger(map, "result", -2);
            /*result: 0：库存空了或未在秒杀时段
                      1：秒杀成功
                      -1：重复秒杀
                      -2：存储过程内部异常
             */
            if (result == 1) {
                SuccessKilled successKill = successKilledMapper.queryByIdWithSeckill(seckillId, userid);
                return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKill);
            } else {
                return new SeckillExecution(seckillId, SeckillStatEnum.stateOf(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
        }
    }
}
