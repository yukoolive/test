package com.yesx.ssm.controller;

import com.yesx.ssm.config.shiroCas.ShiroCasConfiguration;
import com.yesx.ssm.po.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpSession;

/**
 * 跳转至cas server去登录（一个入口）
 */
@Controller
@RequestMapping("")
public class CasLoginController {

    @RequestMapping(value={"", "/","/login"},method=RequestMethod.GET)
    public String loginForm(Model model){
//      return "login";
        return "redirect:" + ShiroCasConfiguration.loginUrl;
    }


    /*@RequestMapping(value = "logout", method = { RequestMethod.GET, RequestMethod.POST })
    public String loginout(HttpSession session) {
        return "redirect:"+ShiroCasConfiguration.casLogoutUrl;
    }*/
}

