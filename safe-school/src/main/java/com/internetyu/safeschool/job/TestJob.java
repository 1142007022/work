package com.internetyu.safeschool.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author JD
 * @projectName safe-school
 * @description: TODO
 * @date 2019/11/6 000619:59
 */
@Component
public class TestJob {

    @Scheduled(cron = "*/30 * * * * *")
    public void test(){
        System.out.println("success");
    }

}
