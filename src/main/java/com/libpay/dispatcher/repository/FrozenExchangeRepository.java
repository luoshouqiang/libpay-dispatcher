package com.libpay.dispatcher.repository;

import com.libpay.dispatcher.entity.FrozenExchange;
import com.libpay.dispatcher.entity.MoneyExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FrozenExchangeRepository extends JpaRepository<FrozenExchange,Integer> {

    public  FrozenExchange findByTraceId( String traceId);
}
