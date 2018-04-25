package com.yesx.ssm.controller;

import com.yesx.ssm.po.User;
import com.yesx.ssm.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @RequestMapping("/home")
    public String index(HttpSession session, ModelMap map, HttpServletRequest request){
//        User user = (User) session.getAttribute("user");

        //System.out.println(request.getUserPrincipal().getName());
        //System.out.println(SecurityUtils.getSubject().getPrincipal());

        User loginUser = userService.queryUserByLoginName(request.getUserPrincipal().getName());
        //System.out.println(JSONObject.toJSONString(loginUser));

        map.put("user",loginUser);
        return "/home";
    }
}
