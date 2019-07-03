package com.libpay.dispatcher.controller;

import com.libpay.dispatcher.entity.MoneyExchange;
import com.libpay.dispatcher.exception.ResponseStatus;
import com.libpay.dispatcher.service.LibpayException;
import com.libpay.dispatcher.service.MoneyPoolService;
import com.libpay.dispatcher.service.PayStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("payDispatcher")
public class DispatcherController {
	private static final Logger logger = LoggerFactory.getLogger(DispatcherController.class);
    @Autowired
    private MoneyPoolService moneyPoolService;

    @GetMapping("/who")
    public ResponseStatus<Long> testGet(){
        System.out.println("-------------------------");
        moneyPoolService.callbackAfterPay("1",PayStatus.SUCCESS,1);
        return new ResponseStatus<Long>();
    }

    @PostMapping("/payDay")
    public ResponseStatus<Long> checkPayDay(@RequestBody MoneyExchange moneyExchange) {
    	logger.info("交易流水号：{}，入参：{}", moneyExchange.getTraceId(), moneyExchange);
        ResponseStatus<Long> response=new ResponseStatus<Long>();
        try{
            boolean payWithDay = moneyPoolService.dispatcherPayWithDay(moneyExchange);
            if(payWithDay) {
            	response.setData(0L);
            } else {
            	response.setData(1L);
            	response.setMsg("资金池金额不足");
            }
        }catch(LibpayException ex){
            response=new ResponseStatus<Long>(ex);
            response.setData(1L);
        }catch(Exception ex){
            response.setData(1L);
            ex.printStackTrace();
            response=new ResponseStatus<Long>();
            response.setCode(ResponseStatus.ErrorCode.SYS_ERROR.getCode());
            response.setMsg("系统异常");
        }
        logger.info("交易流水号：{}，结果：{}", moneyExchange.getTraceId(), response);
        return response;
    }

    @RequestMapping(value = "/updatePayStatus")
    @ResponseBody
    public ResponseStatus<Long> updatePayStatus(HttpServletRequest request,String traceId,int poolType, Integer payStatus) {
    	logger.info("入参：tranceId:{}, payStatus:{}", traceId, payStatus);
        ResponseStatus<Long> response=new ResponseStatus<Long>();
        try{
            //TODO
            moneyPoolService.callbackAfterPay(traceId,PayStatus.SUCCESS,poolType);
            response.setData(0L);
        }catch(LibpayException ex){
            response=new ResponseStatus<Long>(ex);
            response.setData(1L);
        }catch(Exception ex){
            response.setData(1L);
            ex.printStackTrace();
            response=new ResponseStatus<Long>();
            response.setCode(ResponseStatus.ErrorCode.SYS_ERROR.getCode());
            response.setMsg("系统异常");
        }
        logger.info("交易流水号：{}，结果：{}", traceId, response);
        return response;
    }

    @RequestMapping(value = "/rechargeMoneypool")
    @ResponseBody
    public ResponseStatus<Long> saveMoney(@RequestBody MoneyExchange moneyExchange) {
    	ResponseStatus<Long> response=new ResponseStatus<Long>();
    	try{
            moneyPoolService.rechargeMoneypool(moneyExchange);
            response.setData(0L);
        }catch(LibpayException ex){
            response=new ResponseStatus<Long>(ex);
            response.setData(1L);
        }catch(Exception ex){
            response.setData(1L);
            ex.printStackTrace();
            response=new ResponseStatus<Long>();
            response.setCode(ResponseStatus.ErrorCode.SYS_ERROR.getCode());
            response.setMsg("系统异常");
        }
        logger.info("交易流水号：{}，结果：{}", moneyExchange.getTraceId(), response);
        return response;
    }

}
