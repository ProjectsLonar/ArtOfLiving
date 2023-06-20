package com.lonar.artofliving.model;

public class RequestDto {

	private int limit;
	private int offset;
	private String searchfield;
	private String status;
	private int userId;
	
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "RequestDto [limit=" + limit + ", offset=" + offset + ", searchfield=" + searchfield + ", status="
				+ status + ", userId=" + userId + "]";
	}
			
}
