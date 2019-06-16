package com.libpay.dispatcher.timer;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@DisallowConcurrentExecution
@Component
public class SecondDayPayJob implements Job {
    private static Logger logger = LoggerFactory.getLogger("SecondDayPayJob");
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("======================");
    }
}
