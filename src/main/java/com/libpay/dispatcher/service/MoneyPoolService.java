package com.libpay.dispatcher.service;

import com.google.common.base.Optional;
import com.google.common.base.Predicates;
import com.libpay.dispatcher.entity.FrozenExchange;
import com.libpay.dispatcher.entity.MoneyPool;
import com.libpay.dispatcher.repository.FrozenExchangeRepository;
import com.libpay.dispatcher.repository.MoneyExchangeRepository;
import com.libpay.dispatcher.repository.MoneyPoolChangeLogRepository;
import com.libpay.dispatcher.repository.MoneyPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Transactional
    public boolean checkMoneyEnough(PayType payType,BigDecimal loanMoney) throws LibpayException{
       List<MoneyPool> moneyPoolList= moneyPoolRepository.findAll();
        if(CollectionUtils.isEmpty(moneyPoolList)){
            throw new LibpayException("there is no money pool configuration in DB.");
        }
        MoneyPool moneyPool=moneyPoolList.get(0);
        BigDecimal leftMoney=moneyPool.getTotalMoney().subtract(moneyPool.getFrozenMoney());
        int result=leftMoney.compareTo(loanMoney);
        if(result<0){
                return false;
        }else{
            frozenMoney(payType,loanMoney);
           BigDecimal newFrozenMoney= moneyPool.getFrozenMoney().add(loanMoney);
            moneyPool.setFrozenMoney(newFrozenMoney);
            moneyPoolRepository.save(moneyPool);
            return true;
        }
    }
    public void revokeAfterPay(){

    }

    private void frozenMoney(PayType payType,BigDecimal loanMoney){
        FrozenExchange frozenExchange=new FrozenExchange();
        frozenExchange.setFrozenMoney(loanMoney);
        frozenExchange.setStartTime(new Date());
        frozenExchange.setPayType(payType.getTypeId());
        frozenExchange.setStatus(FrozenStatus.FROZEN.getStatusId());
        frozenExchange.setPayStatus(PayStatus.INPROGESS.getStatusId());
        frozenExchangeRepository.save(frozenExchange);
    }
}
