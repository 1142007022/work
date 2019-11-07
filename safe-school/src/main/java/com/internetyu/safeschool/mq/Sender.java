package com.internetyu.safeschool.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author JD
 * @projectName safe-school
 * @description: TODO
 * @date 2019/11/7 000716:23
 */
@Component
public class Sender {

    @Autowired
    AmqpTemplate rabbitmqTemplate;

    public void send(String queueName, String message) {
        System.out.println("发送消息：" + message);
        rabbitmqTemplate.convertAndSend(queueName, message);
    }

}
