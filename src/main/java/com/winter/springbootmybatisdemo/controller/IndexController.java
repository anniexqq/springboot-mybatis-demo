package com.winter.springbootmybatisdemo.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    /**
     * 主页
     * @return
     */
    @RequestMapping({"/","/index"})
    @RequiresPermissions("index:view")
    public String index(){
        return "index";
    }

}
