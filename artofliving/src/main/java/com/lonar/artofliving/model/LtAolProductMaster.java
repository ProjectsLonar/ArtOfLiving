package com.lonar.artofliving.model;

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
@Table(name= "lt_aol_product_master")
@JsonInclude(Include.NON_NULL)
public class LtAolProductMaster extends BaseClass{
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name= "product_id")
	private Long productId;
	
	@Column(name= "product_type_id")
	private Long productTypeId;
	
	@Column(name= "product_name")
	private String productName;
	
	@Column(name= "product_description")
	private String productDescription;
	
	@Column(name= "status")
	private String status;

	
	@Transient
	private String productType;
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	@Override
	public String toString() {
		return "LtAolProductMaster [productId=" + productId + ", productTypeId=" + productTypeId + ", productName="
				+ productName + ", productDescription=" + productDescription + ", status=" + status + ", productType="
				+ productType + "]";
	}
	
	
}