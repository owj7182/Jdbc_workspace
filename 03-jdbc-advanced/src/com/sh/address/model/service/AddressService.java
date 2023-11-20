package com.sh.address.model.service;

import static com.sh.common.JdbcTemplate.*;


import java.sql.Connection;
import java.util.List;

import com.sh.address.model.dao.AddressDao;
import com.sh.address.model.entity.Address;
import com.sh.address.model.vo.MemberAddress;

public class AddressService {
	private AddressDao addressDao = new AddressDao();

	public List<Address> findAll() {
		Connection conn = getConnection();
		List<Address> addresses = addressDao.findAll(conn);
		close(conn);
		
		return addresses;
	}

	public MemberAddress findByMemberId(String memberId) {
		Connection conn = getConnection();
		MemberAddress member = addressDao.findByMemberId(conn, memberId);
		close(conn);
		return member;
	}
	/*
	 *  트랜잭션 처리
	 *  update address set default_addr = 0 where member_id = ?
	 *  update address set default_addr = 1 where id = ?
	 *  
	 */
	
	public int updateDefaultAddress(Address newDefaultAddress) {
		Connection conn = getConnection();
		int result = 0;
		try {
			// dao호출
			// 1. 해당 회원의 모든 주소행에 대해 default_addr = 0 수정
			result = addressDao.updateDefaultAddrToZero(conn, newDefaultAddress.getMemberId());
			// 2. 특정 주소행의 default_addr = 1 수정
			result = addressDao.updateDefaultAddrToOne(conn, newDefaultAddress.getId());
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
				
		return result;
	}

	public int insertAddress(Address newAddress) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = addressDao.insertAddress(conn,newAddress);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

	public int deleteAddress(int id) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = addressDao.deleteAddress(conn, id);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}
}
