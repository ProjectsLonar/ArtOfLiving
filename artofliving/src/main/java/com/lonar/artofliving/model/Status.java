package com.lonar.artofliving.model;

public class Status {
	private int code;
	private String message;
	private Object data;
	private Long recordCount;
	private Long totalCount;
	private Long allRecordsCount;
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public Long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public Long getAllRecordsCount() {
		return allRecordsCount;
	}
	public void setAllRecordsCount(Long allRecordsCount) {
		this.allRecordsCount = allRecordsCount;
	}
	@Override
	public String toString() {
		return "Status [code=" + code + ", message=" + message + ", data=" + data + ", recordCount=" + recordCount
				+ ", totalCount=" + totalCount + ", allRecordsCount=" + allRecordsCount + "]";
	}
		
}
