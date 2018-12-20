package com.winter.springbootmybatisdemo.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("security")
public class SecurityController {

    private Logger logger = LogManager.getLogger(SecurityController.class);

    /**
     * 没有权限
     */
    @RequestMapping("unauthorized")
    public String unauthorized(HttpServletRequest request) {
        String asynch = request.getHeader("X-Requested-With");
        if (asynch == null) {
            return "security/authority/authorization";
        }
        return "security/authority/authorization-json";
    }

}
