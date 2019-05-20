package com.jrl.myemployees.rest.dao;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.jrl.myemployees.rest.model.History;

public interface IHistoryDao {
	List<History> getAllHistory();
	History getHistoryById(int historyId);
	List<History> getHistoryByEmployeeId(int employeeId);
	History addHistory(History history);
	History updateHistory(History history);
	void deleteHistory(int historyId);
	boolean historyExist(int historyId);
	int getHistoryIdByStartDateEmployeeId(LocalDate startDate, int emmployeeId);

}
