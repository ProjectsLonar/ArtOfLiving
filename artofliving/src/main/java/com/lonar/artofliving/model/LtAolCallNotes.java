package com.lonar.artofliving.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name= "lt_aol_call_notes")
@JsonInclude(Include.NON_NULL)
public class LtAolCallNotes extends BaseClass{
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name= "call_note_id")
	private Long callNoteId;
	
	@Column(name= "call_list_id")
	private Long callListId; 
	
	@Column(name= "note")
	private String note;
	
	@Column(name= "note_date")
	private Date noteDate;

	
	public Long getCallNoteId() {
		return callNoteId;
	}

	public void setCallNoteId(Long callNoteId) {
		this.callNoteId = callNoteId;
	}

	public Long getCallListId() {
		return callListId;
	}

	public void setCallListId(Long callListId) {
		this.callListId = callListId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getNoteDate() {
		return noteDate;
	}

	public void setNoteDate(Date noteDate) {
		this.noteDate = noteDate;
	}

	
	@Override
	public String toString() {
		return "LtAolCallNotes [callNoteId=" + callNoteId + ", callListId=" + callListId + ", note=" + note
				+ ", noteDate=" + noteDate + "]";
	}
	
		
}