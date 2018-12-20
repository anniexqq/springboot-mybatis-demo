package com.mq.queue.listener.ptp;

import com.alibaba.fastjson.JSON;
import com.winter.springbootmybatisdemo.model.User;
import com.winter.springbootmybatisdemo.service.UserService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 消息接受 监听信息 点对点模式
 */
@Service
public class UserAddListener {
    @Resource
    UserService userService;

    @JmsListener(destination = "LISTENER_ADD_USER",containerFactory ="ptpContainer")
    public void addUserMessage(Map<String, String> map) {
        try {
            String message = JSON.toJSONString(map);
            System.out.println("UserAddListener监听到了消息：\t"
                    + message);
            String userInfoStr = map.get("userInfo");
            User user = JSON.parseObject(userInfoStr,User.class);
            userService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
