package com.jd.simple.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@EnableScheduling
@Component ("worktask")
public class WorkTask {
    private static final Logger logger = LoggerFactory.getLogger(WorkTask.class);

    @Scheduled(cron="0/2 * * * * *")   // 每月、每天、每小时、每分、每2秒
    public void work() {
        long thread=Thread.currentThread().getId();
        logger.info("Spring Quartz的WorkTask-work()任务被调用！ ["+ thread+"]");
    }
}
