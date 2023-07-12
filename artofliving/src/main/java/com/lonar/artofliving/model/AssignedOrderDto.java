package com.lonar.artofliving.model;

import java.util.List;

public class AssignedOrderDto {
	List<Long> mobileNumber;
	 
	 private Long assignedTo;

	 private Long userId;

	public List<Long> getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(List<Long> mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Long assignedTo) {
		this.assignedTo = assignedTo;
	}

	@Override
	public String toString() {
		return "AssignedOrderDto [mobileNumber=" + mobileNumber + ", assignedTo=" + assignedTo + ", userId=" + userId
				+ "]";
	} 
	 
}
