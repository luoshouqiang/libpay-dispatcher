package com.libpay.dispatcher.repository;

import com.libpay.dispatcher.entity.FrozenExchange;
import com.libpay.dispatcher.entity.MoneyPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyPoolRepository extends JpaRepository<MoneyPool,Integer> {
}
