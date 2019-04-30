package com.jrl.myemployees.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrl.myemployees.rest.dao.IAddressDao;
import com.jrl.myemployees.rest.model.Address;

@Service
public class AddressService implements IAddressService {

	@Autowired
	private IAddressDao dao;
	
	public AddressService(IAddressDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<Address> getAllAddresses() {
		return dao.getAllAddresses();
	}

	@Override
	public Address getAddressById(int addressId) {
		Address address = dao.getAddressById(addressId);
		return address;
	}

	@Override
	public List<Address> getAddressByEmployeeId(int employeeId) {
		return dao.getAddressesByEmployeeId(employeeId);
	}

	@Override
	public boolean addAddress(Address address) {
		if (dao.addressExists(address.getStreetAddress())) {
			return Boolean.FALSE;
		} else {
			dao.addAddress(address);
			return Boolean.TRUE;
		}
	}

	@Override
	public void updateAddress(Address address) {
		dao.updateAddress(address);
		
	}

	@Override
	public void deleteAddress(int addressId) {
		dao.deleteAddress(addressId);
		
	}
	

}
