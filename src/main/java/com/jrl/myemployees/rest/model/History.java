package com.jrl.myemployees.rest.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class History implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String jobDescription;
	
	private LocalDate startDate;
	
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
