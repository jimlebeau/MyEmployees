package com.jrl.myemployees.rest.service;

import java.util.List;

import com.jrl.myemployees.rest.model.History;

public interface IHistoryService {
	List<History> getAllHistory();
	History getHistoryById(int historyId);
	List<History> getHistoryByEmployeeId(int employeeId);
	boolean addHistorry(History history);
	void updateHistory(History history);
	void deleteHistory(int historyInt);

}
