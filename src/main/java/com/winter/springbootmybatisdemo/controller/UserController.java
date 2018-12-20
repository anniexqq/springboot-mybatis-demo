package com.winter.springbootmybatisdemo.controller;

import com.winter.springbootmybatisdemo.model.User;
import com.winter.springbootmybatisdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2017/8/16.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public int addUser(User user) throws Exception{
        return userService.addUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){

        return userService.findAllUser(pageNum,pageSize);
    }


    @ResponseBody
    @RequestMapping(value = "/test")
    public String user(User user) {
        //User user = userMapper.findUserByName("哈方");
        System.out.println("user.getAge()------");
        return "qwe";
        //  return user.getName()+"-----"+user.getAge();
    }
    @ResponseBody
    @RequestMapping(value = "/test/my",method = RequestMethod.POST)
    public   JSONObject  user1(@RequestParam(value="username") String userName) {
        JSONObject obj = new JSONObject();
        obj.put("name", "John");
        obj.put("sex", "male");
        obj.put("age", 22);

        return obj;
        //  return user.getName()+"-----"+user.getAge();
    }
    @ResponseBody
    @RequestMapping(value = "/getById/{id}",produces = {"application/json;charset=UTF-8"})
    public String user(@PathVariable("id") int id) {
        User user = userService.findById(id);
        return user.getPhone();
    }

    @ResponseBody
    @RequestMapping(value = "/getById",method = RequestMethod.POST)
    public User getById(@RequestParam("userId") int id) {
        User user = userService.findById(id);
        return user;
    }
}