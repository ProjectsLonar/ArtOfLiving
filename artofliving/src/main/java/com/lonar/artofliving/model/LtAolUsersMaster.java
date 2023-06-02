package com.lonar.artofliving.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name= "lt_aol_users_master")
@JsonInclude(Include.NON_NULL)
public class LtAolUsersMaster extends BaseClass{
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name= "user_id")
	private Long userId;
	
	@Column(name= "user_name")
	private String userName;
	
	@Column(name= "mobile_number")
	private String mobileNumber;
	
	@Column(name= "status")
	private String status;
	
	@Column(name= "role_id")
	private Long roleId;

	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	
	@Override
	public String toString() {
		return "LtAolUsersMaster [userId=" + userId + ", userName=" + userName + ", mobileNumber=" + mobileNumber
				+ ", status=" + status + ", roleId=" + roleId + "]";
	}
	
		
}