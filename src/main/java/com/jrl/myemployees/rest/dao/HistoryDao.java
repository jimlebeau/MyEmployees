package com.jrl.myemployees.rest.dao;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jrl.myemployees.rest.model.History;
import com.jrl.myemployees.rest.model.HistoryRowMapper;


@Transactional
@Repository
public class HistoryDao implements IHistoryDao{

	private static final Logger logger = LoggerFactory.getLogger(EmployeeDao.class);
	private static final String GETALLHISTORY = "select JobDescription, StartDate, EndDate, EmployeeId, HistoryId from History";
	private static final String GETHISTORYBYEMPLOYEEID = "select JobDescription, StartDate, EndDate, EmployeeId, HistoryId from History where EmployeeId = ?";
	private static final String GETHISTORYBYID = "select JobDescription, StartDate, EndDate, EmployeeId, HistoryId from History where HistoryId = ?";
	private static final String INSERTHISTORY = "insert into History (JobDescription, StartDate, EndDate, EmployeeId) values (?, ?, ?. ?)";
	private static final String GETHISTORYBYSTARTDATE = "select HistoryId from  History where StartDate = ?";
	private static final String UPDATEHISTORY = "update History set JobDescription=?, StartDate=?, EndDate=?, EmployeeId=? where HistoryId = ?";
	private static final String DELETEHISTORY = "delete from History where HistoryId = ?";
	private static final String HISTORYEXISTS = "select count(*) from History where StartDate = ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<History> getAllHistory() {
		RowMapper<History> rowMapper = new HistoryRowMapper();
		return this.jdbcTemplate.query(GETALLHISTORY, rowMapper);
	}

	@Override
	public History getHistoryById(int historyId) {
		RowMapper<History> rowMapper = new BeanPropertyRowMapper<History>(History.class);
		History history =  jdbcTemplate.queryForObject(GETHISTORYBYID, rowMapper, historyId);
		return history;
	}

	@Override
	public List<History> getHistoryByEmployeeId(int employeeId) {
		RowMapper<History> rowMapper = new HistoryRowMapper();
		return this.jdbcTemplate.query(GETHISTORYBYEMPLOYEEID, rowMapper, employeeId);
	}

	@Override
	public void addHistory(History history) {
		jdbcTemplate.update(INSERTHISTORY, history.getJobDescription(), history.getStartDate(), history.getEndDate(), history.getEmployeeId());
		int historyId = jdbcTemplate.queryForObject(GETHISTORYBYSTARTDATE, Integer.class, history.getStartDate());
		history.setHistoryId(historyId);
		
	}

	@Override
	public void updateHistory(History history) {
		jdbcTemplate.update(UPDATEHISTORY, history.getJobDescription(), history.getStartDate(), history.getEndDate(), history.getEmployeeId());
		
	}

	@Override
	public void deleteHistory(int historyId) {
		jdbcTemplate.update(DELETEHISTORY);
		
	}

	@Override
	public boolean historyExists(Date startDate) {
		int count = jdbcTemplate.queryForObject(HISTORYEXISTS, Integer.class, startDate);
		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}

}
