package com.libpay.dispatcher.service;

import com.libpay.dispatcher.entity.MoneyExchange;
import com.libpay.dispatcher.exception.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("payDispatcher")
public class DispatcherController {
    @Autowired
    private MoneyPoolService moneyPoolService;
    @RequestMapping(value = "/payDay")
    @ResponseBody
    public ResponseStatus<Long> checkPayDay(@RequestBody MoneyExchange moneyExchange) {
        ResponseStatus<Long> response=new ResponseStatus<Long>();
        try{
            moneyPoolService.dispatcherPayWithDay(moneyExchange);
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
        return response;
    }

    @RequestMapping(value = "/updatePayStatus")
    @ResponseBody
    public ResponseStatus<Long> updatePayStatus(HttpServletRequest request,int traceId,int payStatus) {
        ResponseStatus<Long> response=new ResponseStatus<Long>();
        try{
            //TODO
            moneyPoolService.callbackAfterPay(traceId,PayStatus.SUCCESS);
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
        return response;
    }
}
