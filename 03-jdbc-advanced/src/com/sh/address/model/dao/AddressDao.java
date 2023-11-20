package com.sh.address.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.sh.address.model.entity.Address;
import com.sh.address.model.exception.AddressException;
import com.sh.address.model.vo.MemberAddress;
import com.sh.member.model.entity.Member;
import com.sh.member.model.exception.MemberException;

public class AddressDao {
	private Properties prop = new Properties();
	
	public AddressDao() {
		try {
			prop.load(new FileReader("resources/address-query.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Address> findAll(Connection conn) {
		String sql = prop.getProperty("findAll");
		List<Address> addresses = new ArrayList<>();
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			try(ResultSet rset = pstmt.executeQuery()) {
				while(rset.next()) {
					addresses.add(handleAddressResultSet(rset));
				}
			}
		} catch (SQLException e) {
			throw new AddressException("전체 조회 오류!!", e);
		}
		
		return addresses;
	}
	
	private Address handleAddressResultSet(ResultSet rset) throws SQLException {
		Address address = new Address();
		address.setId(rset.getInt("id"));
		address.setMemberId(rset.getString("member_id"));
		address.setAddress(rset.getString("address"));
		address.setDefaultAddr(rset.getInt("default_addr") == 1);
		address.setCreatedAt(rset.getDate("created_at"));
		return address;
	}

	public MemberAddress findByMemberId(Connection conn, String memberId) {
		String sql = prop.getProperty("findByMemberId");
		MemberAddress member = null;
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, memberId);
			try (ResultSet rset = pstmt.executeQuery()) {
				if(rset.next()) {
					// 회원관련
					member = new MemberAddress();
					member.setId(rset.getString("id"));
					member.setName(rset.getString("name"));
					member.setGender(rset.getString("gender"));
					member.setBirthday(rset.getDate("birthday"));
					member.setEmail(rset.getString("email"));
					member.setPoint(rset.getInt("point"));
					member.setCreatedAt(rset.getTimestamp("created_at"));
					if(rset.getInt("address_id") != 0) {
						do {
						// 주소관련
							Address addr = new Address();
							addr.setId(rset.getInt("address_id"));
							addr.setMemberId(rset.getString("member_id"));
							addr.setAddress(rset.getString("address"));
							addr.setDefaultAddr(rset.getInt("default_addr") == 1);
							addr.setCreatedAt(rset.getDate("address_created_at"));
							member.addAddress(addr);
						} while (rset.next());
					}	
				}
			}
			
		} catch (SQLException e) {
			throw new AddressException("회원 주소 조회 오류!", e);
		}
		
		return member;
	}

	public int updateDefaultAddrToZero(Connection conn, String memberId) {
		String sql = prop.getProperty("updateDefaultAddrToZero");
		int result = 0;
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, memberId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new AddressException("기본 주소 무효화 오류!", e);
		}
		return result;
	}

	public int updateDefaultAddrToOne(Connection conn, int id) {
		String sql = prop.getProperty("updateDefaultAddrToOne");
		int result = 0;
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new AddressException("기본 주소 지정 오류!", e);
		}
		return result;
	}

	public int insertAddress(Connection conn, Address newAddress) {
		int result = 0;
		String sql = prop.getProperty("insertAddress");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, newAddress.getMemberId());
			pstmt.setString(2, newAddress.getAddress());
			pstmt.setBoolean(3, newAddress.isDefaultAddr());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new AddressException("주소 등록 오류!", e);
		}
		return result;
	}

	public int deleteAddress(Connection conn, int id) {
		int result = 0;
		String sql = prop.getProperty("deleteAddress");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new AddressException("주소 삭제 오류!", e);
		}
		return result;
	}
}
