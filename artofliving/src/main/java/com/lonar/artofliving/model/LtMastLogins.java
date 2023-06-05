package com.lonar.artofliving.model;




import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "lt_mast_logins")
@JsonInclude(Include.NON_NULL)
public class LtMastLogins {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOGIN_ID")
	private Long loginId;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "LOGIN_DATE")
	private Date loginDate;

	@Column(name = "IP_ADDRESS")
	private String ipAddress;

	@Column(name = "DEVICE")
	private String device;

	@Column(name = "OTP")
	private Long otp;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "TOKEN_ID")
	private Long tokenId;
	
	@Transient
	private String mobileNumber;

	public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public Long getOtp() {
		return otp;
	}

	public void setOtp(Long otp) {
		this.otp = otp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getTokenId() {
		return tokenId;
	}

	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Override
	public String toString() {
		return "LtMastLogins [loginId=" + loginId + ", userId=" + userId + ", loginDate=" + loginDate + ", ipAddress="
				+ ipAddress + ", device=" + device + ", otp=" + otp + ", status=" + status + ", tokenId=" + tokenId
				+ ", mobileNumber=" + mobileNumber + "]";
	}


}
