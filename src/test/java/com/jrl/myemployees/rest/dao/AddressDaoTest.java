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
import static org.junit.Assert.assertNull;
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
		address1 = new Address("111 First St", "San Jose", "98765", "CA", 10, 1);
		address2 = new Address("222 Second St", "Palo Alto", "87654", "CA", 11, 2);
		employee = new Employee(1, "firstName1", "lastName1", "email1", BigDecimal.valueOf(1111111), "111223333");
	}
	
	@Test
	public void constructorTest() {
		dao = new AddressDao();
		assertThat(dao, notNullValue());
		
	}
	
	@Test
	public void add_shouldCreateAddress() {
		Address result = dao.addAddress(address1);
		
		assertThat(result.getStreetAddress(), equalTo(address1.getStreetAddress()));
		assertThat(result.getCity(), equalTo(address1.getCity()));
	}
	
	@Test
	public void add_shouldReturnNullAddress() {
		Address address = dao.addAddress(address1);
		Address result = dao.addAddress(address1);
		assertNull(result);
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
		
		Address result = dao.updateAddress(address1);
		
		assertThat(result.getZipCode(), equalTo(address1.getZipCode()));
				
	}
	
	@Test
	public void updateAddress_shouldReturnNull() {
		dao.addAddress(address1);
		Address result = dao.updateAddress(address2);
		assertNull(result);
	}

	@Test
	public void deleteAddress_shouldDeleteAddress() {
		dao.addAddress(address1);
		dao.addAddress(address2);
		dao.deleteAddress(address1.getAddressId());
		assertThat(dao.addressExists(address1.getAddressId()), equalTo(Boolean.FALSE));
	}
	
	
	@Test
	public void addressExists_shouldReturnTrue() {
		dao.addAddress(address1);
		assertThat(dao.addressExists(address1.getAddressId()), equalTo(Boolean.TRUE));
	}
	
	@Test
	public void addressExists_shouldReturnFalse() {
		dao.addAddress(address2);
		assertThat(dao.addressExists(address1.getAddressId()), equalTo(Boolean.FALSE));
	}
	
	@Test
	public void getAddressIdByAddress_shouldReturnAddressId() {
		Address address = dao.addAddress(address1);
		assertThat(dao.getAddressIdByAddress(address1.getEmployeeId(), address1.getStreetAddress(), address1.getCity(), address1.getZipCode()), equalTo(address.getAddressId()));
	}
	
	@Test
	public void getAddressIdByAddress_shoultNotReturnAddressId() {
		dao.addAddress(address2);
		assertThat(dao.getAddressIdByAddress(address1.getEmployeeId(), address1.getStreetAddress(), address1.getCity(), address1.getZipCode()), equalTo(0));
		
	}
}
