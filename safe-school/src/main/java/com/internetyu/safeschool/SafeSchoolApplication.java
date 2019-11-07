package com.internetyu.safeschool;

import com.internetyu.safeschool.netty.NettyServer;
import com.internetyu.safeschool.util.SpringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetSocketAddress;

@SpringBootApplication
@EnableScheduling
public class SafeSchoolApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext =  SpringApplication.run(SafeSchoolApplication.class, args);
        SpringUtils.setApplicationContext(applicationContext);
        //启动服务端
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(new InetSocketAddress("192.168.0.228", 8090));
    }

}
