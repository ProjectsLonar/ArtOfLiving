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
@Table(name= "lt_mast_faq")
@JsonInclude(Include.NON_NULL)
public class LtMastFaq extends BaseClass{
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name= "faq_id")
	private Long faqId;
	
	@Column(name= "faq")
	private String faq;
	
	@Column(name= "faq_ans")
	private String faqAns;
	
	@Column(name= "status")
	private String status;
	
	@Column(name= "sequence_no")
	private Long sequenceNo;
	
	@Column(name= "user_type")
	private String userType;

	
	public Long getFaqId() {
		return faqId;
	}

	public void setFaqId(Long faqId) {
		this.faqId = faqId;
	}

	public String getFaq() {
		return faq;
	}

	public void setFaq(String faq) {
		this.faq = faq;
	}

	public String getFaqAns() {
		return faqAns;
	}

	public void setFaqAns(String faqAns) {
		this.faqAns = faqAns;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Long sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "LtMastFaq [faqId=" + faqId + ", faq=" + faq + ", faqAns=" + faqAns + ", status=" + status
				+ ", sequenceNo=" + sequenceNo + ", userType=" + userType + "]";
	}

	
	
}