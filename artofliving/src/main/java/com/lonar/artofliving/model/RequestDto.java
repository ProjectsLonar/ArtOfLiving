package com.lonar.artofliving.model;

public class RequestDto {

	private int limit;
	private int offset;
	private String searchfield;
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
	@Override
	public String toString() {
		return "RequestDto [limit=" + limit + ", offset=" + offset + ", searchfield=" + searchfield + "]";
	}
	
	
}
