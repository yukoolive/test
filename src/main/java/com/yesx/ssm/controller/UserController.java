package com.yesx.ssm.controller;

import com.yesx.ssm.po.User;
import com.yesx.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/findUser",method = RequestMethod.GET)
    @ResponseBody
    public User findUser(String loginName) {
            //System.out.println(loginName);
            User user = userService.queryUserByLoginName(loginName);
            return user;
    }
}
