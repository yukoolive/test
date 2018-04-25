package com.yesx.ssm.controller;

import com.yesx.ssm.common.BusinessJson;
import com.yesx.ssm.po.User;
import com.yesx.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/solrUser")
public class SolrUserController {

    @Autowired
    UserService userService;

    @RequestMapping("/list")
    public String list(){
        return "/solrUser/list";
    }

    @RequestMapping("/addUser")
    @ResponseBody
    public String addUser(User user){
        if(user==null) return "传入User为空,请重试！";

        boolean flag = userService.setSolrUser(user);
        if(flag)
            return "成功创建索引！";
        else
            return "创建失败索引！";
    }

    @RequestMapping("/selectUser")
    @ResponseBody
    public BusinessJson selectUser(String loginName){
        if(loginName==null) return new BusinessJson(false,null,"传入loginName为空,请重试！",null);

        List<User> userList = userService.selectSolrByLoginName(loginName);
        return new BusinessJson(true,null,"null",userList);
    }
}
