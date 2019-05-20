package com.jrl.myemployees.rest.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.jrl.myemployees.rest.dao.IEmployeeDao;
import com.jrl.myemployees.rest.dao.IHistoryDao;
import com.jrl.myemployees.rest.model.History;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class HistoryServiceTest {

	@Mock
	private IHistoryDao dao;
	private IEmployeeDao employeeDao;
	
	private HistoryService service;
	private List<History> histories;
	private LocalDate testStartDate;
	
	
	@Before
	public void setUp() throws ParseException {
		dao = Mockito.mock(IHistoryDao.class);
		employeeDao = Mockito.mock(IEmployeeDao.class);
		service = new HistoryService(dao, employeeDao);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dateString = "2019-01-21";
		testStartDate = LocalDate.parse(dateString, formatter);
		
		History hist1 = new History("description 1", testStartDate, null, 1, 1);
		History hist2 = new History("description 2", testStartDate, null, 2, 2);
		histories = new ArrayList<>();
		histories.add(hist1);
		histories.add(hist2);
	}
	
	@Test
	public void getAllHistorySuccess() {
		Mockito.when(dao.getAllHistory()).thenReturn(histories);
		assertThat(service.getAllHistory(), hasSize(2));
	}
	
	@Test
	public void getHistoryByEmployeeId() {
		Mockito.when(dao.getHistoryByEmployeeId(1)).thenReturn(histories);
		assertThat(service.getHistoryByEmployeeId(1), hasSize(2));
	}
	
	@Test
	public void getHistoryById() {
		History hist1 = new History("description 1", testStartDate, null, 1, 1);
		Mockito.when(dao.getHistoryById(hist1.getHistoryId())).thenReturn(hist1);
		History result = service.getHistoryById(hist1.getHistoryId());
		assertThat(result.getJobDescription(), equalTo(hist1.getJobDescription()));
	}
	
	@Test
	public void addHistorySuccess() {
		History hist1 = new History("description 1", testStartDate, null, 1, 1);
		
		Mockito.when(dao.getHistoryIdByStartDateEmployeeId(hist1.getStartDate(), hist1.getEmployeeId())).thenReturn(0);
		Mockito.when(employeeDao.employeeExists(hist1.getEmployeeId())).thenReturn(Boolean.TRUE);
		Mockito.when(dao.addHistory(hist1)).thenReturn(hist1);
		
		History result = service.addHistory(hist1);
		
		assertThat(result.getJobDescription(), equalTo(hist1.getJobDescription()));
		assertThat(result.getStartDate(), equalTo(hist1.getStartDate()));
	}
	
	@Test
	public void addHistory_shouldReturnNull_whenAlreadyExist() {
		History hist1 = new History("description 1", testStartDate, null, 1, 1);

		Mockito.when(dao.getHistoryIdByStartDateEmployeeId(hist1.getStartDate(), hist1.getEmployeeId())).thenReturn(1);
		History result = service.addHistory(hist1);
		assertNull(result);
	}
	
	@Test
	public void addHistory_shouldReturnNull_whenEmployeeDoesNotExist() {
		History hist1 = new History("description 1", testStartDate, null, 1, 1);
		
		Mockito.when(dao.getHistoryIdByStartDateEmployeeId(hist1.getStartDate(), hist1.getEmployeeId())).thenReturn(0);
		Mockito.when(employeeDao.employeeExists(hist1.getEmployeeId())).thenReturn(Boolean.FALSE);
		
		History result = service.addHistory(hist1);
		assertNull(result);
	}
	
	@Test
	public void updateHistorySuccess() {
		History hist1 = new History("description 1", testStartDate, null, 1, 1);
		
		Mockito.when(dao.getHistoryById(hist1.getHistoryId())).thenReturn(hist1);
		Mockito.when(employeeDao.employeeExists(hist1.getEmployeeId())).thenReturn(Boolean.TRUE);
		Mockito.when(dao.updateHistory(hist1)).thenReturn(hist1);
		
		History result = service.updateHistory(hist1);
		assertThat(result.getJobDescription(), equalTo(hist1.getJobDescription()));
		assertThat(result.getStartDate(), equalTo(hist1.getStartDate()));

	}
	
	@Test
	public void updateHistory_shouldReturnNull_whenHistoryDoesNotExist() {
		History hist1 = new History("description 1", testStartDate, null, 1, 1);

		Mockito.when(dao.getHistoryById(hist1.getHistoryId())).thenReturn(null);
		History result = service.updateHistory(hist1);
		assertNull(result);
	}
	
	@Test
	public void updateHistory_shouldReturnNull_whenEmployeeDoesNotExist() {
		History hist1 = new History("description 1", testStartDate, null, 1, 1);

		Mockito.when(dao.getHistoryById(hist1.getHistoryId())).thenReturn(hist1);
		Mockito.when(employeeDao.employeeExists(hist1.getHistoryId())).thenReturn(Boolean.FALSE);
		
		History result = service.updateHistory(hist1);
		assertNull(result);
	}
}
