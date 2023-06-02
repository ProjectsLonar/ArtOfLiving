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
	private Status faq;
	
	@Column(name= "faq_ans")
	private Status faqAns;
	
	@Column(name= "status")
	private Status status;
	
	@Column(name= "sequence_no")
	private Long sequenceNo;
	
	@Column(name= "user_type")
	private Status userType;

	
	public Long getFaqId() {
		return faqId;
	}

	public void setFaqId(Long faqId) {
		this.faqId = faqId;
	}

	public Status getFaq() {
		return faq;
	}

	public void setFaq(Status faq) {
		this.faq = faq;
	}

	public Status getFaqAns() {
		return faqAns;
	}

	public void setFaqAns(Status faqAns) {
		this.faqAns = faqAns;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Long sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public Status getUserType() {
		return userType;
	}

	public void setUserType(Status userType) {
		this.userType = userType;
	}

	
	@Override
	public String toString() {
		return "LtMastFaq [faqId=" + faqId + ", faq=" + faq + ", faqAns=" + faqAns + ", status=" + status
				+ ", sequenceNo=" + sequenceNo + ", userType=" + userType + "]";
	}
		
	
}