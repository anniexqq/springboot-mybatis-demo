package com.winter.springbootmybatisdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.winter.springbootmybatisdemo.model.User;
import com.winter.springbootmybatisdemo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送消息
 */
@Service(value="messageService")
public class MessageServiceImpl implements MessageService {
    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsQueueTemplate;

    @Autowired
    @Qualifier("jmsTopicTemplate")
    private JmsTemplate jmsTopicTemplate;

    //法2
/*    @Resource
    @Qualifier("textMsgDestination")
    private Queue textMsgDestination;*/
    //法2
/*    @Autowired
    private JmsMessagingTemplate jmsTemplate;*/

    //法2
/*    @Override
    public void sendTextMessage(String message){
        jmsTemplate.convertAndSend(textMsgDestination,message);
    }*/

    //点对点模式
    @Override
    public void sendTextMessage(String message){
        jmsQueueTemplate.convertAndSend("LISTENER_TEXT_MSG", message);
    }

    //点对点模式
    @Override
    public void sendUserInfo(User user){
        Map<String, String> userInfoMq = new HashMap<>();
        userInfoMq.put("userInfo", JSON.toJSONString(user));
        jmsQueueTemplate.convertAndSend("LISTENER_ADD_USER", userInfoMq);
    }
    //发布订阅模式
    @Override
    public void sendPubSubMessage(String message){
        jmsTopicTemplate.convertAndSend("LISTENER_TEXT_MSG_PUB_SUB",message);
    }

}
