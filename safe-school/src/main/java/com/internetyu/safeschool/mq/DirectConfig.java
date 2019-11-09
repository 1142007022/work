package com.internetyu.safeschool.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JD
 * @projectName safe-school
 * @description: TODO
 * @date 2019/11/7 000716:22
 */
@Configuration
public class DirectConfig {

    @Bean(name = "direct-queue")
    public Queue directQueue(){
        return new Queue("direct",false); //队列名字，是否持久化
    }

    @Bean(name = "direct-queue1")
    public Queue directQueue1(){
        return new Queue("direct",false); //队列名字，是否持久化
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("direct",false,false);//交换器名称、是否持久化、是否自动删除
    }

    @Bean
    Binding binding(@Qualifier("direct-queue") Queue queue, DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("direct");
    }

}
