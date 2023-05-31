package com.lonar.artofliving.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LT_MAST_SMS_TOKENS")
public class LtMastSmsToken {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "SMS_TOKEN_ID")
	private Long smsTokenId;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "TRANSACTION_ID")
	private Long transactionId;
	
	@Column(name = "SEND_DATE")
	private Date sendDate;
	
	@Column(name = "SMS_STATUS")
	private String smsStatus;
	
	@Column(name = "SMS_OBJECT")
	private String smsObject;

	@Column(name = "SEND_TO")
	private Long sendTo;
	
	@Column(name = "SMS_TYPE")
	private String smsType;

	public Long getSmsTokenId() {
		return smsTokenId;
	}

	public void setSmsTokenId(Long smsTokenId) {
		this.smsTokenId = smsTokenId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getSmsStatus() {
		return smsStatus;
	}

	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}

	public String getSmsObject() {
		return smsObject;
	}

	public void setSmsObject(String smsObject) {
		this.smsObject = smsObject;
	}

	public Long getSendTo() {
		return sendTo;
	}

	public void setSendTo(Long sendTo) {
		this.sendTo = sendTo;
	}

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	@Override
	public String toString() {
		return "LtMastSmsToken [smsTokenId=" + smsTokenId + ", userId=" + userId + ", transactionId=" + transactionId
				+ ", sendDate=" + sendDate + ", smsStatus=" + smsStatus + ", smsObject=" + smsObject + ", sendTo="
				+ sendTo + ", smsType=" + smsType + "]";
	}
	
	
	
}
