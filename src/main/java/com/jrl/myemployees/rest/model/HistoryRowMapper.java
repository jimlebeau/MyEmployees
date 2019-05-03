package com.jrl.myemployees.rest.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class HistoryRowMapper implements RowMapper<History>{

	@Override
	public History mapRow(ResultSet rs, int rowNum) throws SQLException {
		History history = new History();
		history.setJobDescription(rs.getString("JobDescription"));
		history.setStartDate(rs.getDate("StartDate").toLocalDate());
		history.setEndDate(rs.getDate("EndDate").toLocalDate());
		history.setEmployeeId(rs.getInt("EmployeeId"));
		history.setHistoryId(rs.getInt("HistoryId"));
		return history;
	}

}
