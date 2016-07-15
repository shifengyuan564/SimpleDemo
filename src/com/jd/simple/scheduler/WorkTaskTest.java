package com.jd.simple.scheduler;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Description:
 * <p/>
 * Author: shifengyuan@jd.com
 * Date: 2015/12/11
 */
public class WorkTaskTest {

    private static ApplicationContext applicationContext  =
            new ClassPathXmlApplicationContext("classpath:testSpring/mybatis/Resources/spring/applicationContext.xml");    //得到容器

    public static void main (String[] args){

        WorkTask wt = (WorkTask) applicationContext.getBean("worktask");
        wt.work();
    }
}
