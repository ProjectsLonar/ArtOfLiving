package com.lonar.artofliving.model;

import java.util.List;

public class LtMasterCallingListErrorDto {

	
	private LtMasterCallingListRequestDto ltMastcallingList;
	private List<String> errorList;
	private Integer rowNo;
	public LtMasterCallingListRequestDto getLtMastcallingList() {
		return ltMastcallingList;
	}
	public void setLtMastcallingList(LtMasterCallingListRequestDto ltMastcallingList) {
		this.ltMastcallingList = ltMastcallingList;
	}
	public List<String> getErrorList() {
		return errorList;
	}
	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}
	public Integer getRowNo() {
		return rowNo;
	}
	public void setRowNo(Integer rowNo) {
		this.rowNo = rowNo;
	}
	@Override
	public String toString() {
		return "LtMasterCallingListErrorDto [ltMastcallingList=" + ltMastcallingList + ", errorList=" + errorList
				+ ", rowNo=" + rowNo + "]";
	}
	
	
}
