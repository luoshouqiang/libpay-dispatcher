package com.libpay.dispatcher.repository;

import com.libpay.dispatcher.entity.MoneyExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyExchangeRepository extends JpaRepository<MoneyExchange,Integer> {
}
