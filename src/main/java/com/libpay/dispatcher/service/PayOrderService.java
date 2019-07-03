package com.libpay.dispatcher.service;

import com.libpay.dispatcher.entity.SwipeRecord;
import com.libpay.dispatcher.repository.SwipeRecordRepository;
import org.libpay.client.LibPay;
import org.libpay.client.model.order.OrderBuilder;
import org.libpay.client.model.order.PayOrder;
import org.libpay.client.model.order.TransOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PayOrderService {


    @Autowired
    SwipeRecordRepository swipeRecordRepository;
//TODO ,need change marchId? what is the unit of amount?
    private void payOrderForClient(String exchangeId){
//        SwipeRecord swipeRecord= swipeRecordRepository.findByExchangeId(exchangeId);
//        OrderBuilder orderBuilder=new OrderBuilder();
//        orderBuilder.amount(swipeRecord.getReceivedAmount().intValue()).body("客户实际到账金额")
//                .subject("客户实际到账金额支付测试").device("pc").channelId(getPayType(swipeRecord.getPayType())).
//                mchOrderNo(exchangeId);
//        TransOrder order=orderBuilder.buildTransOrder();
        //TODO need move this class to this project and add post transOrder
        //LibPay.postOrder();
    }

    private void payOrderForAgent(String exchangeId){
        SwipeRecord swipeRecord= swipeRecordRepository.findByExchangeId(exchangeId);
        OrderBuilder orderBuilder=new OrderBuilder();
        orderBuilder.amount(swipeRecord.getRebateAmount().intValue()).body("代理商实际到账金额")
                .subject("代理商实际到账金额支付测试").device("pc").channelId(getPayType(swipeRecord.getPayType())).
                mchOrderNo(exchangeId);
        TransOrder order=orderBuilder.buildTransOrder();
        //TODO need move this class to this project and add post transOrder
        //LibPay.postOrder();
    }
    public void payForExchangeId(String exchangeId){
        payOrderForClient(exchangeId);
        payOrderForAgent(exchangeId);

    }

    //just query for T1 order list which need pay
    public   List<SwipeRecord> getNeedPayOrderList(){
        java.util.Calendar calendar= java.util.Calendar.getInstance();
        Date now= calendar.getTime();
        calendar.add(Calendar.DATE,-1);
        Date beforeDate=calendar.getTime();
        List<SwipeRecord> swipeRecordList=swipeRecordRepository.findSwipeRecordsByCreateTimeBetweenAndReceivedTypeAndAndStatus(beforeDate,now,1,1);
        return swipeRecordList;
    }
    private String getPayType(int payType){
        if(payType==0){
            return "WX_JSAPI";
        }else if(payType==1){
            return "ALIPAY_WAP";
        }
        return "";
    }
}
