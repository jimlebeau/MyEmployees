package com.jrl.myemployees.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrl.myemployees.rest.dao.IEmployeeDao;
import com.jrl.myemployees.rest.dao.IHistoryDao;
import com.jrl.myemployees.rest.model.History;

@Service
public class HistoryService implements IHistoryService {

	@Autowired
	private IHistoryDao dao;
	private IEmployeeDao employeeDao;
	
	public HistoryService(IHistoryDao dao, IEmployeeDao employeeDao) {
		this.dao = dao;
		this.employeeDao = employeeDao;
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
	public History addHistory(History history) {
		History createdHistory = null;
		if (dao.getHistoryIdByStartDateEmployeeId(history.getStartDate(), history.getEmployeeId()) == 0) {
			if (employeeDao.employeeExists(history.getEmployeeId())) {
				createdHistory = dao.addHistory(history);
			}
		}
		return createdHistory;
	}

	@Override
	public History updateHistory(History history) {
		History updatedHistory = null;
		if (history.getHistoryId() != null && history.getHistoryId() != 0) {
			if (dao.getHistoryById(history.getHistoryId()) != null) {
				if (employeeDao.employeeExists(history.getEmployeeId())) {
					updatedHistory = dao.updateHistory(history);
				}
			}
		}
		return updatedHistory;
	}

	@Override
	public void deleteHistory(int historyId) {
		dao.deleteHistory(historyId);
		
	}
	
	
}
