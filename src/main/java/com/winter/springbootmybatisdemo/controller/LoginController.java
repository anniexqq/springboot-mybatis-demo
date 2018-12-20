package com.winter.springbootmybatisdemo.controller;

import com.alibaba.fastjson.JSON;
import com.winter.springbootmybatisdemo.model.User;
import com.winter.springbootmybatisdemo.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "login")
public class LoginController {

    private Logger logger = LogManager.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

/*
   @RequestMapping("/loginPage")
    public ModelAndView list() {
        ModelAndView mv;
        mv = new ModelAndView("login");//地址指向templates/login.html
        return mv;
    }
*/

    @RequestMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    //验证登录
    @ResponseBody
    @RequestMapping(value = "/checkLogin", produces = {"application/json;charset=UTF-8"})
    public String checkLogin(User user){
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),
                user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            logger.info("登录失败!!!{}", JSON.toJSONString(user));
            return "0";
        }
        return "1";
    }

}
