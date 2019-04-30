package com.jrl.myemployees.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrl.myemployees.rest.dao.IHistoryDao;
import com.jrl.myemployees.rest.model.History;

@Service
public class HistoryService implements IHistoryService {

	@Autowired
	private IHistoryDao dao;
	
	public HistoryService(IHistoryDao dao) {
		this.dao = dao;
	}

	@Override
	public List<History> getAllHistory() {
		return dao.getAllHistory();
	}

	@Override
	public History getHistoryById(int historyId) {
		History history = dao.getHistoryById(historyId);
		return history;
	}

	@Override
	public List<History> getHistoryByEmployeeId(int employeeId) {
		return dao.getHistoryByEmployeeId(employeeId);
	}

	@Override
	public boolean addHistorry(History history) {
		if (dao.historyExists(history.getStartDate())) {
			return Boolean.FALSE;
		} else {
			dao.addHistory(history);
			return Boolean.TRUE;
		}
	}

	@Override
	public void updateHistory(History history) {
		dao.updateHistory(history);
		
	}

	@Override
	public void deleteHistory(int historyId) {
		dao.deleteHistory(historyId);
		
	}
	
	
}
