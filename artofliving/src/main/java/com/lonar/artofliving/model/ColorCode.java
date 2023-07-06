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
@Table(name= "lt_aol_status_master")
@JsonInclude(Include.NON_NULL)
public class ColorCode extends BaseClass {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name= "status_id")
	private Long statusId;
	
	
	@Column(name= "status")
	private String status;
	
	@Column(name= "color_code")
	private String colorCode;

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	@Override
	public String toString() {
		return "ColorCode [statusId=" + statusId + ", status=" + status + ", colorCode=" + colorCode + "]";
	}
	
	
	
	
}
