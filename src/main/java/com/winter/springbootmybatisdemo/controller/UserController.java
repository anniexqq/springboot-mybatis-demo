package com.winter.springbootmybatisdemo.controller;

import com.winter.springbootmybatisdemo.model.User;
import com.winter.springbootmybatisdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/add")
    public int addUser(@Valid @RequestBody User user) throws Exception{
        return userService.addUser(user);
    }

    //http://localhost:8081/user/all?pageNum=1&pageSize=5
    @PostMapping(value = "/all")
    public List<User> findAllUser(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize){
        return userService.findAllUser(pageNum,pageSize);
    }

    @PostMapping(value = "/getById")
    public User getById(@RequestParam("userId") int id) {
        User user = new User();
        user.setUserId(id);
        user = userService.selectOne(user);
        return user;
    }
}