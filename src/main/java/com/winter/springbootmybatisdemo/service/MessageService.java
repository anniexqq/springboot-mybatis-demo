package com.winter.springbootmybatisdemo.service;

import com.winter.springbootmybatisdemo.model.User;

public interface MessageService {

    void sendTextMessage(String message);

    void sendUserInfo(User user);

    void sendPubSubMessage(String message);

}
