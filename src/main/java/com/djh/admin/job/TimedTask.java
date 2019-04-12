package com.djh.admin.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务.
 */
@Slf4j
@Component
public class TimedTask {
    // 每隔5秒执行一次："*/5 * * * * ?"
    // 每隔1分钟执行一次
    @Scheduled(cron = "0 */30 * * * ?")
    public void go(){
        log.info("定时任务......");
    }
}
