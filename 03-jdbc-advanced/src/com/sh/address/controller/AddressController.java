package com.sh.address.controller;

import java.util.List;

import com.sh.address.model.entity.Address;
import com.sh.address.model.service.AddressService;
import com.sh.address.model.vo.MemberAddress;

public class AddressController {
	private AddressService addressService = new AddressService();

	public List<Address> findAll() {
		List<Address> addresses = null;
		try {
			addresses = addressService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다 : " + e.getMessage());
		}
		return addresses;
	}

	public MemberAddress findByMemberId(String memberId) {
		MemberAddress member = null;
		try {
			member = addressService.findByMemberId(memberId);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다 : " + e.getMessage());
		}
		return member;
	}

	public int updateDefaultAddress(Address newDefaultAddress) {
		int result = 0;
		try {
			result = addressService.updateDefaultAddress(newDefaultAddress);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다 : " + e.getMessage());
		}
		return 0;
	}

	public int insertAddress(Address newAddress) {
		int result = 0;
		try {
			result = addressService.insertAddress(newAddress);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다 : " + e.getMessage());
		}
		return result;
	}

	public int deleteAddress(int id) {
		int result = 0;
		result = addressService.deleteAddress(id);
		return result;
	}
}
