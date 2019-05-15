package com.jrl.myemployees.rest.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jrl.myemployees.rest.model.Address;
import com.jrl.myemployees.rest.model.AddressRowMapper;


@Transactional
@Repository
public class AddressDao implements IAddressDao {

	private static final Logger logger = LoggerFactory.getLogger(AddressDao.class);
	private static final String GETALLADDRESSES = "select StreetAddress, City, ZipCode, State, EmployeeId, AddressId from addresses";
	private static final String GETADDRESSESBYID = "select StreetAddress, City, ZipCode, State, EmployeeId, AddressId from addresses where AddressId = ?";
	private static final String GETADDRESSESBYEMPLOYEEID = "select StreetAddress, City, ZipCode, State, EmployeeId, AddressId from addresses where EmployeeId = ?";
	private static final String INSERTADDRESSES = "insert into Addresses (StreetAddress, City, ZipCode, State, EmployeeId) values (?, ?, ?, ?, ?)";
	private static final String GETADDRESSBYSTREETADDRESS = "select AddressId from Addresses where EmployeeId = ? and StreetAddress = ? and City = ? and ZipCode = ?";
	private static final String UPDATEADDRESS = "update Addresses set StreetAddress=?, City=?, ZipCode=?, State=?, EmployeeId=? where AddressId = ?";
	private static final String DELETEADDRESS = "delete from Addresses where AddressId = ?";
	private static final String ADDRESSEXISTS = "select count(*) from Addresses where AddressId = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Address> getAllAddresses() {
		RowMapper<Address> rowMapper = new AddressRowMapper();
		return this.jdbcTemplate.query(GETALLADDRESSES, rowMapper);
	}

	@Override
	public Address getAddressById(int addressId) {
		RowMapper<Address> rowMapper = new BeanPropertyRowMapper<Address>(Address.class);
		Address address = null;
		try {
			address = jdbcTemplate.queryForObject(GETADDRESSESBYID, rowMapper, addressId);
			
		} catch (EmptyResultDataAccessException ex) {
			
		}
		return address;
	}

	@Override
	public List<Address> getAddressesByEmployeeId(int employeeId) {
		RowMapper<Address> rowMapper = new AddressRowMapper();
		return this.jdbcTemplate.query(GETADDRESSESBYEMPLOYEEID, rowMapper, employeeId);
	}

	@Override
	public Address addAddress(Address address) {
		int addressExist = getAddressIdByAddress(address.getEmployeeId(), address.getStreetAddress(), address.getCity(), address.getZipCode());
		if (addressExist != 0) {
			// address already exist
			return null;
		}
		jdbcTemplate.update(INSERTADDRESSES, address.getStreetAddress(), address.getCity(), address.getZipCode(), address.getState(), address.getEmployeeId());
		int addressId = jdbcTemplate.queryForObject(GETADDRESSBYSTREETADDRESS, Integer.class, address.getEmployeeId(), address.getStreetAddress(), address.getCity(), address.getZipCode());
		address.setAddressId(addressId);
		return address;
		
	}

	@Override
	public Address updateAddress(Address address) {
		Address updatedAddress = null;
		if (addressExists(address.getAddressId())) {
			jdbcTemplate.update(UPDATEADDRESS, address.getStreetAddress(), address.getCity(), address.getZipCode(), address.getState(), address.getEmployeeId(), address.getAddressId());
			updatedAddress = getAddressById(address.getAddressId());
		}
		return updatedAddress;
		
	}

	@Override
	public void deleteAddress(int addressId) {
		jdbcTemplate.update(DELETEADDRESS, addressId);
		
	}

	@Override
	public boolean addressExists(int addressId) {
		int count = jdbcTemplate.queryForObject(ADDRESSEXISTS, Integer.class, addressId);
		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public int getAddressIdByAddress(int employeeId, String streetAddress, String city, String zipCode) {
		int addressId = 0;
		try {
			addressId = jdbcTemplate.queryForObject(GETADDRESSBYSTREETADDRESS, Integer.class, employeeId, streetAddress, city, zipCode);
			
		} catch (EmptyResultDataAccessException ex) {
			
		}
		return addressId;
	}

}
