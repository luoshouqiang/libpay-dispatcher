package com.libpay.dispatcher.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the money_pool_change_log database table.
 * 
 */
@Entity
@Table(name="money_pool_change_log")
public class MoneyPoolChangeLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="before_money_number")
	private BigDecimal beforeMoneyNumber;

	@Column(name="change_money_number")
	private BigDecimal changeMoneyNumber;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="change_time")
	private Date changeTime;

	@Column(name="current_money")
	private BigDecimal currentMoney;

	@Column(name="frozen_id")
	private int frozenId;

	@Column(name="pay_id")
	private int payId;

	public MoneyPoolChangeLog() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getBeforeMoneyNumber() {
		return this.beforeMoneyNumber;
	}

	public void setBeforeMoneyNumber(BigDecimal beforeMoneyNumber) {
		this.beforeMoneyNumber = beforeMoneyNumber;
	}

	public BigDecimal getChangeMoneyNumber() {
		return this.changeMoneyNumber;
	}

	public void setChangeMoneyNumber(BigDecimal changeMoneyNumber) {
		this.changeMoneyNumber = changeMoneyNumber;
	}

	public Date getChangeTime() {
		return this.changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public BigDecimal getCurrentMoney() {
		return this.currentMoney;
	}

	public void setCurrentMoney(BigDecimal currentMoney) {
		this.currentMoney = currentMoney;
	}

	public int getFrozenId() {
		return this.frozenId;
	}

	public void setFrozenId(int frozenId) {
		this.frozenId = frozenId;
	}

	public int getPayId() {
		return this.payId;
	}

	public void setPayId(int payId) {
		this.payId = payId;
	}

}