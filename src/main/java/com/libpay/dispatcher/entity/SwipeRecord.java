package com.libpay.dispatcher.entity;



import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 刷卡记录
 * </p>
 *
 * @author luoshouqiang
 * @since 2019-06-30
 */
@Entity
@Table(name="swipe_record")
public class SwipeRecord  extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	/**
	 * member_id
	 */

	private Integer memberId;

	/**
	 * 客户名称
	 */

	private String customerName;

	/**
	 * 交易金额
	 */

	private BigDecimal transactionAmount;

	/**
	 * 交易时间
	 */
	private Date transactionTime;

	/**
	 * 到账金额
	 */
	private BigDecimal receivedAmount;

	/**
	 * 到账类型(0:实时到账 1:次日到账)
	 */
	private Integer receivedType;

	/**
	 * 支付类型 0微信  1支付宝 2银行卡
	 */
	private Integer payType;

	/**
	 * 回扣金额
	 */
	private BigDecimal rebateAmount;

	/**
	 * 费率
	 */
	private BigDecimal rate;

	/**
	 * 手续费
	 */
	private BigDecimal serviceFee;

	/**
	 * 收款方卡号
	 */
	private String payeeCardNo;

	/**
	 * 交易流水ID
	 */
	private String exchangeId;

	/**
	 * 状态(0:生成记录1:成功2:失败)
	 */
	private Integer status;

	/**
	 * 付款方member_id
	 */
	private Integer payerMemberId;

	/**
	 * 付款方姓名
	 */
	private String payerName;

	/**
	 * 付款方手机
	 */
	private String payerPhone;

	/**
	 * 付款方卡号
	 */
	private String payerCardNo;

	/**
	 * 付款方身份证
	 */
	private String payerIdCardNo;

	/**
	 * 失败原因
	 */
	private String failReason;

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	/**
	 * 更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	public BigDecimal getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(BigDecimal receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public Integer getReceivedType() {
		return receivedType;
	}

	public void setReceivedType(Integer receivedType) {
		this.receivedType = receivedType;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public BigDecimal getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(BigDecimal rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getPayeeCardNo() {
		return payeeCardNo;
	}

	public void setPayeeCardNo(String payeeCardNo) {
		this.payeeCardNo = payeeCardNo;
	}

	public String getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPayerMemberId() {
		return payerMemberId;
	}

	public void setPayerMemberId(Integer payerMemberId) {
		this.payerMemberId = payerMemberId;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayerPhone() {
		return payerPhone;
	}

	public void setPayerPhone(String payerPhone) {
		this.payerPhone = payerPhone;
	}

	public String getPayerCardNo() {
		return payerCardNo;
	}

	public void setPayerCardNo(String payerCardNo) {
		this.payerCardNo = payerCardNo;
	}

	public String getPayerIdCardNo() {
		return payerIdCardNo;
	}

	public void setPayerIdCardNo(String payerIdCardNo) {
		this.payerIdCardNo = payerIdCardNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}



	@Override
	public String toString() {
		return "SwipeRecord{" + "id=" + id + ", memberId=" + memberId + ", customerName=" + customerName
				+ ", transactionAmount=" + transactionAmount + ", transactionTime=" + transactionTime
				+ ", receivedAmount=" + receivedAmount + ", receivedType=" + receivedType + ", payType=" + payType
				+ ", rebateAmount=" + rebateAmount + ", rate=" + rate + ", serviceFee=" + serviceFee + ", payeeCardNo="
				+ payeeCardNo + ", exchangeId=" + exchangeId + ", status=" + status + ", payerMemberId=" + payerMemberId
				+ ", payerName=" + payerName + ", payerPhone=" + payerPhone + ", payerCardNo=" + payerCardNo
				+ ", payerIdCardNo=" + payerIdCardNo + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ "}";
	}
}
