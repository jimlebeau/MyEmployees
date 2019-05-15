package com.jrl.myemployees.rest.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AddressRowMapper implements RowMapper<Address>{

	@Override
	public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
		Address address = new Address();
		address.setStreetAddress(rs.getString("StreetAddress"));
		address.setCity(rs.getString("City"));
		address.setZipCode(rs.getString("ZipCode"));
		address.setState(rs.getString("State"));
		address.setEmployeeId(rs.getInt("EmployeeId"));
		address.setAddressId(rs.getInt("AddressId"));
		return address;
	}

}
