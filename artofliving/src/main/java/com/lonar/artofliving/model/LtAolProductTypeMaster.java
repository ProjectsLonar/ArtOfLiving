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
@Table(name= "lt_aol_product_type_master")
@JsonInclude(Include.NON_NULL)
public class LtAolProductTypeMaster extends BaseClass{
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name= "product_type_id")
	private Long productTypeId;
	
	@Column(name= "product_type")
	private String productType;
	
	@Column(name= "status")
	private String status;

	
	public Long getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	@Override
	public String toString() {
		return "LtAolProductTypeMaster [productTypeId=" + productTypeId + ", productType=" + productType + ", status="
				+ status + "]";
	}
	
	
}