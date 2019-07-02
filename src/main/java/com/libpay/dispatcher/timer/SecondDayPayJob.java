package com.libpay.dispatcher.timer;

import com.libpay.dispatcher.entity.SwipeRecord;
import com.libpay.dispatcher.repository.SwipeRecordRepository;
import com.libpay.dispatcher.service.PayOrderService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@DisallowConcurrentExecution
@Component
public class SecondDayPayJob implements Job {
    private static Logger logger = LoggerFactory.getLogger("SecondDayPayJob");
    @Autowired
    SwipeRecordRepository swipeRecordRepository;

    @Autowired
    PayOrderService payOrderService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<SwipeRecord> recordList= payOrderService.getNeedPayOrderList();
        if(CollectionUtils.isEmpty(recordList)){
            logger.info("There is no T+1 orders to need to execute!");
        }
        for(SwipeRecord swipeRecord:recordList){
            payOrderService.payForExchangeId(swipeRecord.getExchangeId());
        }

    }


}
