package com.mq.queue.listener.pubsub;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * 消息接受 监听信息 发表订阅模式
 */
@Service
public class TextListener2 {
    @JmsListener(destination = "LISTENER_TEXT_MSG_PUB_SUB",containerFactory ="pubsubContainer")
    public void textMessage(String message) {
        try {
            System.out.println("订阅者-2--监听到了文本消息：\t"
                    + message);
            //do something ...
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
