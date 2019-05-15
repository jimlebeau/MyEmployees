package com.jrl.myemployees.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrl.myemployees.rest.dao.IAddressDao;
import com.jrl.myemployees.rest.dao.IEmployeeDao;
import com.jrl.myemployees.rest.model.Address;

@Service
public class AddressService implements IAddressService {

	@Autowired
	private IAddressDao dao;
	private IEmployeeDao employeeDao;
	
	public AddressService(IAddressDao dao, IEmployeeDao employeeDao) {
		this.dao = dao;
		this.employeeDao = employeeDao;
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
	public Address addAddress(Address address) {
		Address createdAddress  = null;
		if (dao.getAddressIdByAddress(address.getEmployeeId(), address.getStreetAddress(), address.getCity(), address.getZipCode()) == 0) {
			// address does not already exist
			System.out.println("addAddress = " + address.toString());
			if (employeeDao.employeeExists(address.getEmployeeId())) {
				// employee exist
				createdAddress =  dao.addAddress(address);
			}
		}
		return createdAddress;
	}

	@Override
	public Address updateAddress(Address address) {
		Address updatedAddress = null;
		if (address.getAddressId() != null && address.getAddressId() != 0) {
			if (dao.getAddressById(address.getAddressId()) != null) {
				if (employeeDao.employeeExists(address.getEmployeeId())) {
					updatedAddress = dao.updateAddress(address);
				}				
			}
		} 
		return updatedAddress;
	}

	@Override
	public void deleteAddress(int addressId) {
		dao.deleteAddress(addressId);
		
	}
	

}
