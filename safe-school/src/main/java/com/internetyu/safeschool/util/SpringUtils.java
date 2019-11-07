package com.internetyu.safeschool.util;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author JD
 * @projectName safe-school
 * @description: TODO
 * @date 2019/11/7 000711:37
 */
@Component
public class SpringUtils {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    //通过name获取 Bean.
    public static Object getBean(String name) {
        return applicationContext.getBean(name);

    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }
}
