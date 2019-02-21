package com.qg.utils;

import org.apache.activemq.broker.jmx.DestinationView;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

/***
 * ActiveMQ 工具类
 */
@Component
public class ActiveMQUtils {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    /***
     * 发送队列消息
     * @param name
     * @param message
     */
    public void sendQueueMesage(String name,Object message){
        // 定义目的地
        Destination destination = new ActiveMQQueue(name);
        // 发送消息
        jmsMessagingTemplate.convertAndSend(destination, message);
    }
}
