package com.grgbanking.netty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class SchedulerTask {

    private static Logger logger = Logger.getLogger("SchedulerTask");

    @Autowired
    public ConcurrentPerformanceServer nettyServerTest;

    /**
     * @Author Smith
     * @Description 设置没6秒执行一次
     * @Date 14:23 2019/1/24
     * @Param
     * @return void
     **/
    @Scheduled(cron = "0 * * * * ?")
    private void process() throws InterruptedException {
        logger.info("-------");
        logger.info("start quatz!!!");
        nettyServerTest.run();
    }

}
