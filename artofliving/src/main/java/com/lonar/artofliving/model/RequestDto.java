package com.lonar.artofliving.model;

public class RequestDto {

	private int limit;
	private int offset;
	private String searchfield;
	private String status;
	private Long userId;
	private Long callListId;
	private String role;
	private Long mobileNumber;
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getSearchfield() {
		return searchfield;
	}
	public void setSearchfield(String searchfield) {
		this.searchfield = searchfield;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCallListId() {
		return callListId;
	}
	public void setCallListId(Long callListId) {
		this.callListId = callListId;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	@Override
	public String toString() {
		return "RequestDto [limit=" + limit + ", offset=" + offset + ", searchfield=" + searchfield + ", status="
				+ status + ", userId=" + userId + ", callListId=" + callListId + ", role=" + role + "]";
	}
	public Long getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
}
