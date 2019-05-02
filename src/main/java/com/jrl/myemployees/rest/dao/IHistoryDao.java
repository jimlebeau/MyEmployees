package com.jrl.myemployees.rest.dao;

import java.util.Date;
import java.util.List;

import com.jrl.myemployees.rest.model.History;

public interface IHistoryDao {
	List<History> getAllHistory();
	History getHistoryById(int historyId);
	List<History> getHistoryByEmployeeId(int employeeId);
	void addHistory(History history);
	void updateHistory(History history);
	void deleteHistory(int historyId);
	boolean historyExists(Date startDate);

}
