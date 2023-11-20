package com.sh.address.model.vo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sh.address.model.entity.Address;
import com.sh.member.model.entity.Member;

/**
 * vo클래스
 * - 실제 테이블로 존재하지는 않지만, join등 확장된 형태의 resultset을 처리하기 위한 클래스
 *
 */

public class MemberAddress extends Member{
	private List<Address> addresses = new ArrayList<>();

	public MemberAddress() {
		super();
	}

	public MemberAddress(String id, String name, String gender, Date birthday, String email, int point,
			Timestamp createdAt) {
		super(id, name, gender, birthday, email, point, createdAt);
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	/**
	 * 주소 추가 편의 메소드
	 * @param address
	 */
	public void addAddress(Address address) {
		this.addresses.add(address);
	}

	@Override
	public String toString() {
		return "MemberAddress [toString()=" + super.toString() + ", addresses=" + addresses + "]";
	}

	
	

}
	
	
