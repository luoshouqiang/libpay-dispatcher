package com.libpay.dispatcher.service;

import com.google.common.base.Optional;
import com.google.common.base.Predicates;
import com.libpay.dispatcher.entity.FrozenExchange;
import com.libpay.dispatcher.entity.MoneyExchange;
import com.libpay.dispatcher.entity.MoneyPool;
import com.libpay.dispatcher.repository.FrozenExchangeRepository;
import com.libpay.dispatcher.repository.MoneyExchangeRepository;
import com.libpay.dispatcher.repository.MoneyPoolChangeLogRepository;
import com.libpay.dispatcher.repository.MoneyPoolRepository;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class MoneyPoolService {
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
            checkMoneyEnough(traceId,loanMoney);
        }catch(LibpayException | HibernateOptimisticLockingFailureException |
                StaleObjectStateException | TransactionSystemException ex){
            return false;
        }
        return true;
    }

    public void callbackAfterPay(int traceId,PayStatus payStatus){
        revokeAfterPay(traceId,payStatus);
    }


    @Transactional
    protected void checkMoneyEnough(int traceId, BigDecimal loanMoney) throws LibpayException {
        MoneyPool moneyPool = getMoneyPool();
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

    private MoneyPool getMoneyPool() {
        List<MoneyPool> moneyPoolList = moneyPoolRepository.findAll();
        if (CollectionUtils.isEmpty(moneyPoolList)) {
            throw new LibpayException("there is no money pool configuration in DB.");
        }
        return moneyPoolList.get(0);
    }



    @Transactional
    protected void revokeAfterPay(int traceId,PayStatus payStatus) {
        MoneyPool moneyPool = getMoneyPool();
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

    private FrozenExchange unfrozenMoney(int traceId, PayStatus payStatus) {
        FrozenExchange frozenExchange=frozenExchangeRepository.findByTraceId(traceId);
        if(frozenExchange==null){
            throw new LibpayException("The frozen exchange can not be find ,traceId is"+traceId);
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
        frozenExchangeRepository.save(frozenExchange);
    }
}
