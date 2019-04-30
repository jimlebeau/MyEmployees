package com.jrl.myemployees.rest.model;

import java.io.Serializable;
import java.util.Date;

public class History implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String jobDescription;
	
	private Date startDate;
	
	private Date endDate;
	
	private Integer employeeId;
	
	private Integer historyId;
	
	public History() {
		
	}

	public History(String jobDescription, Date startDate, Date endDate, Integer employeeId, Integer historyId) {
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
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
