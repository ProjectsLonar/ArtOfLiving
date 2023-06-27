package com.lonar.artofliving.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lonar.artofliving.model.LtAolUserProducts;

@JsonInclude(Include.NON_NULL)
public class ResponseDto {

	private String student_name;
	private String gender;
	private String mobile_number;
	private String note;
	private Date dob;
	private String course_name;
	private String status;
	private Long  callListId;
	private Long callNoteId;
	private List<LtAolUserProducts> coursesList;
	
	
	public String getStudent_name() {
		return student_name;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getMobile_number() {
		return mobile_number;
	}
	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Long getCallListId() {
		return callListId;
	}
	public void setCallListId(Long callListId) {
		this.callListId = callListId;
	}
	public Long getCallNoteId() {
		return callNoteId;
	}
	public void setCallNoteId(Long callNoteId) {
		this.callNoteId = callNoteId;
	}
	public List<LtAolUserProducts> getCoursesList() {
		return coursesList;
	}
	public void setCoursesList(List<LtAolUserProducts> coursesList) {
		this.coursesList = coursesList;
	}
	@Override
	public String toString() {
		return "ResponseDto [student_name=" + student_name + ", gender=" + gender + ", mobile_number=" + mobile_number
				+ ", note=" + note + ", dob=" + dob + ", course_name=" + course_name + ", status=" + status
				+ ", callListId=" + callListId + ", callNoteId=" + callNoteId + ", coursesList=" + coursesList + "]";
	}
	

	

	
		
}
