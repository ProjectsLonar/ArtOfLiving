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
@Table(name= "lt_aol_call_list_master")
@JsonInclude(Include.NON_NULL)
public class LtAolCallListMaster extends BaseClass{
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name= "call_list_id")
	private Long callListId;
	
	@Column(name= "mobile_number")
	private String mobileNumber;
	
	@Column(name= "student_name")
	private String studentName;
	
	@Column(name= "call_source")
	private String callSource;
	
	@Column(name= "gender")
	private String gender;
	
	@Column(name= "dob")
	private Date dob;
	
	@Column(name= "notes_id")
	private String notesId;
	
	@Column(name= "call_date")
	private Date callDate;
	
	@Column(name= "assigned_to")
	private Long assignedTo;
	
	@Column(name= "email")
	private String email;
	
	@Column(name= "address")
	private String address;
	
	@Column(name= "city")
	private String city;
	
	@Column(name= "pin_code")
	private String pinCode;

	@Transient
	Long userId;
	
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCallListId() {
		return callListId;
	}

	public void setCallListId(Long callListId) {
		this.callListId = callListId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getCallSource() {
		return callSource;
	}

	public void setCallSource(String callSource) {
		this.callSource = callSource;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getNotesId() {
		return notesId;
	}

	public void setNotesId(String notesId) {
		this.notesId = notesId;
	}

	public Date getCallDate() {
		return callDate;
	}

	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}

	public Long getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Long assignedTo) {
		this.assignedTo = assignedTo;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	@Override
	public String toString() {
		return "LtAolCallListMaster [callListId=" + callListId + ", mobileNumber=" + mobileNumber + ", studentName="
				+ studentName + ", callSource=" + callSource + ", gender=" + gender + ", dob=" + dob + ", notesId="
				+ notesId + ", callDate=" + callDate + ", assignedTo=" + assignedTo + ", email=" + email + ", address="
				+ address + ", city=" + city + ", pinCode=" + pinCode + ", userId=" + userId + "]";
	}

	
}
