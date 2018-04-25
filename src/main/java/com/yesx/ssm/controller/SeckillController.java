package com.yesx.ssm.controller;

import com.yesx.ssm.dto.Exposer;
import com.yesx.ssm.dto.SeckillExecution;
import com.yesx.ssm.dto.SeckillResult;
import com.yesx.ssm.enums.SeckillStatEnum;
import com.yesx.ssm.exception.RepeatKillException;
import com.yesx.ssm.exception.SeckillCloseException;
import com.yesx.ssm.po.Seckill;
import com.yesx.ssm.po.User;
import com.yesx.ssm.service.SeckillService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
@Controller
@RequestMapping("/seckill")
public class SeckillController {
    @Autowired
    private SeckillService seckillService;



    @RequestMapping(value = "/list",method= RequestMethod.GET)
    public String list(Model model) {
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "/seckill/list";
    }

    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public String detail(Long seckillId, Model model){
        if(seckillId == null){
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if(seckill == null){
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "/seckill/details";
    }

    //ajax ,json暴露秒杀接口的方法
    //@RequestMapping(value="/exposer",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @RequestMapping(value="/exposer",method = RequestMethod.POST)
    @ResponseBody
    public SeckillResult<Exposer> exposer(Long seckillId){
        SeckillResult<Exposer> result;

        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true,exposer);
        } catch (Exception e) {
            e.printStackTrace();
            result = new SeckillResult<Exposer>(false,e.getMessage());
        }

        return result;
    }


    @RequestMapping(value="/execution", method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute( Long seckillId, String md5){
        /*if(phone == null){
            return new SeckillResult<SeckillExecution>(false,"未注册");
        }*/
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        SeckillResult<SeckillExecution> result;

        try {
            SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId,user.getId(), md5);
            return new SeckillResult<SeckillExecution>(true,execution);
        } catch (RepeatKillException e1) {
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(true,execution);
        } catch(SeckillCloseException e2){
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.END);
            return new SeckillResult<SeckillExecution>(true,execution);
        }catch(Exception e){
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(true,execution);
        }
    }

    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();
        return new SeckillResult<Long>(true,now.getTime());
    }

}
