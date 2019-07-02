package com.libpay.dispatcher.repository;


import com.libpay.dispatcher.entity.SwipeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface SwipeRecordRepository extends JpaRepository<SwipeRecord,Integer> {
    public List<SwipeRecord> findSwipeRecordsByCreateTimeBetweenAndrAndReceivedTypeAndAndStatus(Date beforeDate, Date today, int receivedType, int status);

    SwipeRecord findByExchangeId(String exchangeId);
}
