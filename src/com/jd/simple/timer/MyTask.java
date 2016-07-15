package com.jd.simple.timer;

import java.util.TimerTask;

/**
 * Description:
 * <p/>
 * Author: shifengyuan@jd.com
 * Date: 2015/11/30
 */
public class MyTask extends TimerTask {
    @Override
    public void run() {
        System.out.println("MyTask Run !!");
    }
}
