package com.mq.queue.listener.ptp;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * 消息接受 监听信息 点对点模式
 */
@Service
public class TextListener {
    @JmsListener(destination = "LISTENER_TEXT_MSG",containerFactory ="ptpContainer")
    public void textMessage(String message) {
        try {
            System.out.println("textListener监听到了文本消息：\t"
                    + message);
            //do something ...
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
