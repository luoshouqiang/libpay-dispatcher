package com.libpay.dispatcher.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.libpay.dispatcher.entity.MoneyPool;

@Repository
public interface MoneyPoolRepository extends JpaRepository<MoneyPool,Integer> {

//	@Modifying
//    @Transactional
//	@Query(value = "update MoneyPool m set m.totalMoney = m.totalMoney + :actualMoney")
//	MoneyPool addMoney(@Param("actualMoney") BigDecimal actualMoney);
//	@Query(value = "update MoneyPool m set m.totalMoney = :totalMoney")
//	MoneyPool update(@Param("totalMoney") BigDecimal totalMoney);
	
	MoneyPool findByPoolType(int poolType);
}
