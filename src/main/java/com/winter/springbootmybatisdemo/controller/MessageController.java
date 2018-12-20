package com.winter.springbootmybatisdemo.controller;

import com.winter.springbootmybatisdemo.model.User;
import com.winter.springbootmybatisdemo.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@ResponseBody
@RequestMapping(value = "/msg")
public class MessageController {
    @Resource
    MessageService messageService;

    @PostMapping(value = "/addUser")
    public void addUser(@Valid @RequestBody User user){
        messageService.sendUserInfo(user);
    }
    @PostMapping(value = "/sendMsg")
    public void sendMsg(@Valid @RequestBody String msg){
        messageService.sendTextMessage(msg);
    }
    //发布订阅模式
    @PostMapping(value = "/pubSubMsg")
    public void pubSubMsg(@Valid @RequestBody String msg){
        messageService.sendPubSubMessage(msg);
    }
}
