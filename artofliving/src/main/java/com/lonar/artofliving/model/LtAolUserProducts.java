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
@Table(name= "lt_aol_user_products")
@JsonInclude(Include.NON_NULL)
public class LtAolUserProducts extends BaseClass{
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name= "user_course_id")
	private Long userCourseId;
	
	@Column(name= "call_list_id")
	private Long callListId;
	
	@Column(name= "product_id")
	private Long productId;
	
	@Column(name= "status")
	private String status;
	
	@Column(name= "effective_date")
	private Date effectiveDate;
	
	@Column(name= "place_name")
	private String placeName;
	
	@Transient
	private Long userId;
	
	@Transient 
	private String productName;
	
	public Long getUserCourseId() {
		return userCourseId;
	}

	public void setUserCourseId(Long userCourseId) {
		this.userCourseId = userCourseId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public Long getCallListId() {
		return callListId;
	}

	public void setCallListId(Long callListId) {
		this.callListId = callListId;
	}
	
	

	@Override
	public String toString() {
		return "LtAolUserProducts [userCourseId=" + userCourseId + ", userId=" + userId + ", productId=" + productId
				+ ", status=" + status + ", effectiveDate=" + effectiveDate + ", placeName=" + placeName
				+ ", callListId=" + callListId + "]";
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}


	
}