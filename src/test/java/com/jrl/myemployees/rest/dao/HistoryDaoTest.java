package com.jrl.myemployees.rest.dao;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.jrl.myemployees.rest.model.History;
import com.jrl.myemployees.rest.model.Employee;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RunWith(SpringRunner.class)
@JdbcTest
@ComponentScan
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class HistoryDaoTest {

	@Autowired
	private HistoryDao dao;
	
	private History history1;
	private History history2;
	private LocalDate testStartDate;
	private LocalDate testEndDate;
	
	private Employee employee;
	
	@Before
	public void setup() throws ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String startDateString = "2019-03-01";
		String endDateString = "2019-05-21";
		testStartDate = LocalDate.parse(startDateString, formatter);
		testEndDate = LocalDate.parse(endDateString, formatter);
		
		history1 = new History("job description 1", testStartDate, testEndDate, 1, 1);
		history2 = new History("job description 2", testEndDate, testStartDate, 2, 2);
		employee = new Employee(1, "firstName1", "lastName1", "email1", BigDecimal.valueOf(1111111), "111223333");
	}

	@Test
	public void constructorTest() {
		dao = new HistoryDao();
		assertThat(dao, notNullValue());
		
	}
	
	@Test
	public void getAllHistory_shouldReturnHistoryes() {
		dao.addHistory(history1);
		dao.addHistory(history2);
		
		List<History> histories = dao.getAllHistory();
		assertThat(histories, hasSize(2));
	}
	
	@Test
	public void getHistoryById() {
		
	}
	
	@Test
	public void add_shouldCreateHistory() {
		History result = dao.addHistory(history1);
		
		assertThat(result.getJobDescription(), equalTo(history1.getJobDescription()));
		assertThat(result.getStartDate(), equalTo(history1.getStartDate()));
	}
	
	@Test
	public void addHistory_shouldReturnNull_whenExist() {
		History history = dao.addHistory(history1);
		History result = dao.addHistory(history1);
		assertNull(result);
	}
	
	@Test
	public void updateHistory_shouldUpdateHistory() {
		String newJobDescription = "new job description";
		
		dao.addHistory(history1);
		history1.setJobDescription(newJobDescription);
		History result = dao.updateHistory(history1);
		
		assertThat(result.getJobDescription(), equalTo(history1.getJobDescription()));
				
	}
	
	@Test
	public void updateHistory_shouldReturnNull_whenHistoryDoesNotExist() {
//		dao.addHistory(history1);
		History result = dao.updateHistory(history2);
		assertNull(result);
	}
	
	@Test
	public void historyExist_shouldReturnTrue() {
		History addedHistory = dao.addHistory(history1);
		assertThat(dao.historyExist(addedHistory.getHistoryId()), equalTo(Boolean.TRUE));
	}
	
	@Test
	public void historyExists_shouldReturnFalse() {
		assertThat(dao.historyExist(history2.getHistoryId()), equalTo(Boolean.FALSE));
	}
	
	@Test
	public void getHistoryIdByStartDate_shouldReturnTrue() {
		History history = dao.addHistory(history1);
		assertThat(dao.getHistoryIdByStartDateEmployeeId(history.getStartDate(), history.getEmployeeId()), equalTo(history1.getHistoryId()));
	}
	
	@Test
	public void getHistoryIdByStartDate_shouldReturnFalse() {
		dao.addHistory(history1);
		assertThat(dao.getHistoryIdByStartDateEmployeeId(history2.getStartDate(), history2.getEmployeeId()), equalTo(0));
		
	}
	
}
