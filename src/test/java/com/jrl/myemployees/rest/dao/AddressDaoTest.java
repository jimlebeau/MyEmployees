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

import com.jrl.myemployees.rest.model.Address;
import com.jrl.myemployees.rest.model.Employee;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@JdbcTest
@ComponentScan
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class AddressDaoTest {

	@Autowired
	private AddressDao dao;
	
	private Address address1;
	private Address address2;
	private Employee employee;
	
	@Before
	public void setup() {
		address1 = new Address("111 First St", "San Jose", "98765", "CA", null, 1);
		address2 = new Address("222 Second St", "Palo Alto", "87654", "CA", null, 2);
		employee = new Employee(1, "firstName1", "lastName1", "email1", BigDecimal.valueOf(1111111));
	}
	
	@Test
	public void constructorTest() {
		dao = new AddressDao();
		assertThat(dao, notNullValue());
		
	}
	
	@Test
	public void add_shouldCreateAddress_AndGetById() {
		dao.addAddress(address1);
		
		Address result = dao.getAddressById(address1.getAddressId());
		assertThat(result.getStreetAddress(), equalTo(address1.getStreetAddress()));
		assertThat(result.getCity(), equalTo(address1.getCity()));
	}
	
	@Test
	public void getAllAddresses_shouldReturnAddresses() {
		dao.addAddress(address1);
		dao.addAddress(address2);
		
		List<Address> addresses = dao.getAllAddresses();
		assertThat(addresses, hasSize(2));
	}
	
	@Test
	public void updateAddress_shouldUpdateAddress() {
		String newZip = "11111";
		
		dao.addAddress(address1);
		address1.setZipCode(newZip);
		
		dao.updateAddress(address1);
		
		Address result = dao.getAddressById(address1.getAddressId());
		
		assertThat(result.getZipCode(), equalTo(address1.getZipCode()));
				
	}
	
	@Test
	public void deleteAddress_shouldDeleteAddress() {
		dao.addAddress(address1);
		dao.addAddress(address2);
		dao.deleteAddress(address1.getAddressId());
		assertThat(dao.addressExists(address1.getStreetAddress()), equalTo(Boolean.FALSE));
	}
	
	@Test
	public void addressExists_shouldReturnTrue() {
		dao.addAddress(address1);
		assertThat(dao.addressExists(address1.getStreetAddress()), equalTo(Boolean.TRUE));
	}
	
	@Test
	public void addressExists_shouldReturnFalse() {
		dao.addAddress(address1);
		assertThat(dao.addressExists(address2.getStreetAddress()), equalTo(Boolean.FALSE));
	}
}
