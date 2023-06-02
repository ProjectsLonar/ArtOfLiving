package com.lonar.artofliving.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name= "lt_aol_call_history")
@JsonInclude(Include.NON_NULL)
public class LtAolCallHistory extends BaseClass{
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name= "call_his_id")
	private Long callHisId;
	
	@Column(name= "call_list_id")
	private Long callListId;
	
	@Column(name= "call_date")
	private Date callDate;
	
	@Column(name= "user_id") 
	private Long userId;
	
	
	public Long getCallHisId() {
		return callHisId;
	}
	public void setCallHisId(Long callHisId) {
		this.callHisId = callHisId;
	}
	public Long getCallListId() {
		return callListId;
	}
	public void setCallListId(Long callListId) {
		this.callListId = callListId;
	}
	public Date getCallDate() {
		return callDate;
	}
	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	@Override
	public String toString() {
		return "LtAolCallHistory [callHisId=" + callHisId + ", callListId=" + callListId + ", callDate=" + callDate
				+ ", userId=" + userId + "]";
	}
	
	
}