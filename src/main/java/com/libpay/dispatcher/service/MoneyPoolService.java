package com.libpay.dispatcher.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.CollectionUtils;

import com.libpay.dispatcher.entity.FrozenExchange;
import com.libpay.dispatcher.entity.MoneyExchange;
import com.libpay.dispatcher.entity.MoneyPool;
import com.libpay.dispatcher.repository.FrozenExchangeRepository;
import com.libpay.dispatcher.repository.MoneyExchangeRepository;
import com.libpay.dispatcher.repository.MoneyPoolChangeLogRepository;
import com.libpay.dispatcher.repository.MoneyPoolRepository;

@Service
public class MoneyPoolService {
	private static final Logger logger = LoggerFactory.getLogger(MoneyPoolService.class);
    @Autowired
    MoneyExchangeRepository moneyExchangeRepository;
    @Autowired
    MoneyPoolRepository moneyPoolRepository;
    @Autowired
    MoneyPoolChangeLogRepository moneyPoolChangeLogRepository;
    @Autowired
    FrozenExchangeRepository frozenExchangeRepository;

    public boolean dispatcherPayWithDay(MoneyExchange moneyExchange){
        try {

            moneyExchangeRepository.save(moneyExchange);
            BigDecimal loanMoney=moneyExchange.getActualMoney();
            int traceId=moneyExchange.getTraceId();
            checkMoneyEnough(traceId,loanMoney,moneyExchange.getPoolType());
        }catch(LibpayException | HibernateOptimisticLockingFailureException |
                StaleObjectStateException | TransactionSystemException ex){
            return false;
        }
        return true;
    }

    public void callbackAfterPay(String traceId,PayStatus payStatus, int poolType){
        revokeAfterPay(traceId,payStatus,poolType);
    }

    @Transactional
    public boolean saveMoney(MoneyExchange  moneyExchange) {
        try {
            BigDecimal actualMoney = moneyExchange.getActualMoney();
            if(actualMoney.compareTo(new BigDecimal("0")) < 0) {
            	logger.error("交易流水号为：{}存入金额小于0",moneyExchange.getTraceId());
            	return false;
            }
            MoneyPool moneyPool = getMoneyPoolByType(moneyExchange.getPoolType());
            if(null == moneyPool) {
            	logger.error("资金池没有金额数据");
            	return false;
            }
            BigDecimal totalMoney = moneyPool.getTotalMoney().add(actualMoney);
            moneyPool.setTotalMoney(totalMoney);
//        	try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
            moneyExchangeRepository.save(moneyExchange);
            moneyPoolRepository.save(moneyPool);
//            moneyPoolRepository.addMoney(actualMoney);
        }catch(LibpayException | HibernateOptimisticLockingFailureException |
                StaleObjectStateException | TransactionSystemException ex){
            return false;
        }
        return true;
    }

    private MoneyPool getMoneyPoolByType(int poolType) {
        MoneyPool moneyPoolt = moneyPoolRepository.findByPoolType(poolType);
        if (null == moneyPoolt) {
            throw new LibpayException("there is no money pool configuration in DB.");
        }
        return moneyPoolt;
	}

	@Transactional
    protected void checkMoneyEnough(int traceId, BigDecimal loanMoney, int poolType) throws LibpayException {
        MoneyPool moneyPool = getMoneyPoolByType(poolType);
        System.out.println(moneyPool.getTotalMoney());
        System.out.println(moneyPool.getFrozenMoney());
        BigDecimal leftMoney = moneyPool.getTotalMoney().subtract(moneyPool.getFrozenMoney());
        int result = leftMoney.compareTo(loanMoney);
        if (result < 0) {
           throw new LibpayException("There is no enough money");
        } else {
            frozenMoney(traceId,loanMoney);
            BigDecimal newFrozenMoney = moneyPool.getFrozenMoney().add(loanMoney);
            moneyPool.setFrozenMoney(newFrozenMoney);
            moneyPoolRepository.save(moneyPool);


        }
    }

//    private MoneyPool getMoneyPool() {
//        List<MoneyPool> moneyPoolList = moneyPoolRepository.findAll();
//        if (CollectionUtils.isEmpty(moneyPoolList)) {
//            throw new LibpayException("there is no money pool configuration in DB.");
//        }
//        return moneyPoolList.get(0);
//    }



    @Transactional
    protected void revokeAfterPay(String traceId,PayStatus payStatus, int poolType) {
        MoneyPool moneyPool = getMoneyPoolByType(poolType);
        BigDecimal totalMoney=moneyPool.getTotalMoney();
        BigDecimal totalFrozenMoney=moneyPool.getFrozenMoney();
        FrozenExchange unfrozenMoneyExchange= unfrozenMoney(traceId, payStatus);
        BigDecimal frozenMoney=unfrozenMoneyExchange.getFrozenMoney();
        BigDecimal newTotalMoney=totalMoney;
        BigDecimal newFrozenMoney=totalFrozenMoney;
        switch (payStatus){
            case SUCCESS:
                newTotalMoney= totalMoney.subtract(frozenMoney);
                newFrozenMoney=totalFrozenMoney.subtract(frozenMoney);
                 break;
            case FAILD:
                newFrozenMoney=totalFrozenMoney.subtract(frozenMoney);
                break;
        }
        moneyPool.setTotalMoney(newTotalMoney);
        moneyPool.setFrozenMoney(newFrozenMoney);
        moneyPoolRepository.save(moneyPool);
    }

    private FrozenExchange unfrozenMoney(String traceId, PayStatus payStatus) {
        FrozenExchange frozenExchange=frozenExchangeRepository.findByTraceId(traceId);
        if(frozenExchange==null){
            throw new LibpayException("The frozen exchange can not be find ,traceId is "+traceId);
        }
        frozenExchange.setStatus(payStatus.getStatusId());
        frozenExchange.setStatus(FrozenStatus.UNFROZEN.getStatusId());
        frozenExchange.setEndTime(new Date());
        return frozenExchangeRepository.save(frozenExchange);
    }

    private void frozenMoney(int traceId,BigDecimal loanMoney) {
        FrozenExchange frozenExchange = new FrozenExchange();
        frozenExchange.setFrozenMoney(loanMoney);
        frozenExchange.setStartTime(new Date());
//        frozenExchange.setPayType(payType.getTypeId());
        frozenExchange.setStatus(FrozenStatus.FROZEN.getStatusId());
        frozenExchange.setPayStatus(PayStatus.INPROGESS.getStatusId());
        frozenExchange.setTraceId(traceId);
        System.out.println(frozenExchange);
        frozenExchangeRepository.save(frozenExchange);
    }
}
