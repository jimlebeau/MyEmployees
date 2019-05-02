package com.jrl.myemployees.rest.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.jrl.myemployees.rest.dao.IHistoryDao;
import com.jrl.myemployees.rest.model.History;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class HistoryServiceTest {

	@Mock
	private IHistoryDao dao;
	
	private HistoryService service;
	private List<History> histories;
	private Date testStartDate;
	
	
	@Before
	public void setUp() throws ParseException {
		dao = Mockito.mock(IHistoryDao.class);
		service = new HistoryService(dao);
		String dateString = "01/05/2019";
		testStartDate = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
		
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
		
		Mockito.when(dao.historyExists(hist1.getStartDate())).thenReturn(Boolean.FALSE);
		Mockito.doNothing().when(dao).addHistory(hist1);
		
		Boolean result = service.addHistory(hist1);
		
		assertThat(result, equalTo(Boolean.TRUE));
	}
	
	@Test
	public void addHistoryFail() {
		History hist1 = new History("description 1", testStartDate, null, 1, 1);

		Mockito.when(dao.historyExists(hist1.getStartDate())).thenReturn(Boolean.TRUE);
		Boolean result = service.addHistory(hist1);
		assertThat(result, equalTo(Boolean.FALSE));
	}
}
