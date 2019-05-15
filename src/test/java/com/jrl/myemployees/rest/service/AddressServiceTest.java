package com.jrl.myemployees.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.jrl.myemployees.rest.dao.IAddressDao;
import com.jrl.myemployees.rest.dao.IEmployeeDao;
import com.jrl.myemployees.rest.model.Address;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceTest {

	@Mock
	private IAddressDao dao;
	private IEmployeeDao employeeDao;
	
	private AddressService service;
	private List<Address> addresses;
	
	@Before
	public void setUp() {
		dao = Mockito.mock(IAddressDao.class);
		employeeDao = Mockito.mock(IEmployeeDao.class);
		service = new AddressService(dao, employeeDao);
		Address address1 = new Address("street address 1", "city1", "zip1", "state1", 1, 1);
		Address address2 = new Address("street address 2", "city2", "zip2", "state2", 2, 2);
		addresses = new ArrayList<>();
		addresses.add(address1);
		addresses.add(address2);
	}
	
	@Test
	public void getAllAddressSuccess() {
		Mockito.when(dao.getAllAddresses()).thenReturn(addresses);
		assertThat(service.getAllAddresses(), hasSize(2));
	}
	
	@Test
	public void getAddressByEmployeeIdSuccess() {
		Mockito.when(dao.getAddressesByEmployeeId(1)).thenReturn(addresses);
		assertThat(service.getAddressByEmployeeId(1), hasSize(2));
	}
	
	@Test
	public void getAddressByIdSuccess() {
		Address address = new Address("street address 1", "city1", "zip1", "state1", 1, 1);
		Mockito.when(dao.getAddressById(1)).thenReturn(address);
		Address result = service.getAddressById(1);
		System.out.println(result.toString());
		assertThat(result.getStreetAddress(), equalTo(address.getStreetAddress()));
		assertThat(result.getState(), equalTo(address.getState()));		
	}
	
	@Test
	public void addAddressSuccess() {
		Address address = new Address("street address 1", "city1", "zip1", "state1", 1, 1);
		
		Mockito.when(dao.getAddressIdByAddress(address.getEmployeeId(), address.getStreetAddress(), address.getCity(), address.getZipCode())).thenReturn(0);
		Mockito.when(employeeDao.employeeExists(address.getEmployeeId())).thenReturn(Boolean.TRUE);
		Mockito.when(dao.addAddress(address)).thenReturn(address);
		
		Address result = service.addAddress(address);
		
		assertThat(result.getStreetAddress(), equalTo(address.getStreetAddress()));
		assertThat(result.getZipCode(), equalTo(address.getZipCode()));
		assertThat(result.getState(), equalTo(address.getState()));
	}
	
	@Test 
	public void addAddress_shouldReturnNull_whenAlreadyExist() {
		Address address = new Address("street address 1", "city1", "zip1", "state1", 1, 1);

		Mockito.when(dao.getAddressIdByAddress(address.getEmployeeId(), address.getStreetAddress(), address.getCity(), address.getZipCode())).thenReturn(0);
		Address result = service.addAddress(address);
		assertNull(result);
	}
	
	@Test
	public void addAddress_shouldReturnNull_whenEmployeeDoesNotExist() {
		Address address = new Address("street address 1", "city1", "zip1", "state1", 1, 1);
		
		Mockito.when(dao.getAddressIdByAddress(address.getEmployeeId(), address.getStreetAddress(), address.getCity(), address.getZipCode())).thenReturn(0);
		Mockito.when(employeeDao.employeeExists(address.getEmployeeId())).thenReturn(Boolean.FALSE);
//		Mockito.when(dao.addAddress(address)).thenReturn(address);
		
		Address result = service.addAddress(address);
		assertNull(result);
		
	}
	
	@Test
	public void updateAddressSuccess() {
		Address address = new Address("street address 1", "city1", "zip1", "state1", 1, 1);
		
		Mockito.when(dao.getAddressById(address.getAddressId())).thenReturn(address);
		Mockito.when(employeeDao.employeeExists(address.getEmployeeId())).thenReturn(Boolean.TRUE);
		Mockito.when(dao.updateAddress(address)).thenReturn(address);
		
		Address result = service.updateAddress(address);
		assertThat(result.getStreetAddress(), equalTo(address.getStreetAddress()));
		
	}
	
	@Test
	public void updateAddress_shouldReturnNull_whenAddressDoesNotExit() {
		Address address = new Address("street address 1", "city1", "zip1", "state1", 1, 1);

		Mockito.when(dao.getAddressById(address.getAddressId())).thenReturn(null);
		Address result = service.updateAddress(address);
		assertNull(result);
		
	}
	
	@Test
	public void updateAddress_shouldReturnNull_whenEmployeeDoesNotExist() {
		Address address = new Address("street address 1", "city1", "zip1", "state1", 1, 1);
		
		Mockito.when(dao.getAddressById(address.getAddressId())).thenReturn(address);
		Mockito.when(employeeDao.employeeExists(address.getEmployeeId())).thenReturn(Boolean.FALSE);

		Address result = service.updateAddress(address);
		assertNull(result);
	}
}
