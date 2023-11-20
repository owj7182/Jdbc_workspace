package com.sh.tb_user.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import com.sh.tb_user.model.entity.Tb_user;
import com.sh.tb_user.model.exception.UserException;

public class Tb_userDao {
	Properties prop = new Properties();
	
	public Tb_userDao() {
		try {
			prop.load(new FileReader("resources/tb_user-query.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Tb_user handleUserResultSet(ResultSet rset) throws SQLException {
		Tb_user user = new Tb_user();
		user.setId(rset.getString("id"));
		user.setPassword(rset.getString("password"));
		user.setName(rset.getString("name"));
		user.setGender(rset.getString("gender"));
		user.setBirthday(rset.getDate("birthday"));
		user.setPhone(rset.getString("phone"));
		user.setEmail(rset.getString("email"));
		user.setCreatedAt(rset.getDate("created_at"));
		user.setBalance(rset.getInt("balance"));
		user.setPoint(rset.getInt("point"));
		user.setIsManager(rset.getString("is_manager"));
		user.setFavoriteGenre(rset.getString("favorite_genre"));
		return user;
	}

	public List<Tb_user> findById(Connection conn, String id) {
		List<Tb_user> users = new ArrayList<>();
		String sql = prop.getProperty("findById");
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			try(ResultSet rset = pstmt.executeQuery()) {
				while(rset.next()) {
					users.add(handleUserResultSet(rset));
				}
			}
		} catch (SQLException e) {
			throw new UserException("아이디 조회 오류!", e);
		}
		return users;
	}

	public int insertUser(Connection conn, Tb_user user) {
		int result = 0;
		String sql = prop.getProperty("insertUser");
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, user.getId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getGender());
			pstmt.setDate(5, user.getBirthday());
			pstmt.setString(6, user.getPhone());
			pstmt.setString(7, user.getEmail());
			pstmt.setString(8, user.getFavoriteGenre());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			
			throw new UserException("회원가입 오류!", e);
		}
		return result;
	}

	public int deleteUser(Connection conn, String id) {
		int result = 0;
		String sql = prop.getProperty("deleteUser");
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new UserException("> 회원 탈퇴 오류!");
		}
		return result;
	}

	public int updateUserName(Connection conn, String id, String newName) {
		int result = 0;
		String sql = prop.getProperty("updateUserName");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, newName);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new UserException("> 회원 이름 수정 오류!!");
		}
		return result;
	}

	public int updateUserBirthday(Connection conn, String id, Date newBirthday) {
		int result = 0;
		String sql = prop.getProperty("updateUserBirthday");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setDate(1, newBirthday);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new UserException("> 회원 생일 수정 오류!!");
		}
		
		return result;
	}

	public int updateUserPhone(Connection conn, String id, String newPhone) {
		int result = 0;
		String sql = prop.getProperty("updateUserPhone");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, newPhone);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new UserException("> 회원 전화번호 수정 오류!!");
		}
		return result;
	}

	public int updateUserEmail(Connection conn, String id, String newEmail) {
		int result = 0;
		String sql = prop.getProperty("updateUserEmail");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, newEmail);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new UserException("> 회원 이메일 수정 오류!!");
		}
		return result;
	}

	public int updateUserFavoriteGenre(Connection conn, String id, String newFavoriteGenre) {
		int result = 0;
		String sql = prop.getProperty("updateUserFavoriteGenre");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, newFavoriteGenre);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new UserException("> 회원 좋아하는 장르 수정 오류!!");
		}
		return result;
	}
}
