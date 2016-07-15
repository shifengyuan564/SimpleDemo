package com.jd.simple.timer;

import java.util.Timer;

/**
 * Description: Java Timer类的简单使用
 *
 * public void schedule(TimerTask task,long delay,long period)
 * 参数一：是一个实现TimerTask的类的对象，代表我们要执行的任务
 * 参数二：第一次执行任务的延迟时间
 * 参数三：任务执行间隔
 */


public class TimerTest {
    public static void main (String[] args){
        Timer timer = new Timer();
        timer.schedule(new MyTask(), 60*1000, 1000);
    }
}
