package com.libpay.dispatcher.repository;

import com.libpay.dispatcher.entity.MoneyPool;
import com.libpay.dispatcher.entity.MoneyPoolChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyPoolChangeLogRepository extends JpaRepository<MoneyPoolChangeLog,Integer> {
}
