package com.jrl.myemployees.rest.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class History implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String jobDescription;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
//	@NotEmpty
	private LocalDate startDate;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate endDate;
	
	private Integer employeeId;
	
	private Integer historyId;
	
	public History() {
		
	}

	public History(String jobDescription, LocalDate startDate, LocalDate endDate, Integer employeeId, Integer historyId) {
		super();
		this.jobDescription = jobDescription;
		this.startDate = startDate;
		this.endDate = endDate;
		this.employeeId = employeeId;
		this.historyId = historyId;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getHistoryId() {
		return historyId;
	}
	
	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}
	
	@Override
	public String toString() {
		return "History [jobDescription=" + jobDescription + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", employeeId=" + employeeId + ", historyId=" + historyId +"]";
	}

}
