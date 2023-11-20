package com.sh.member.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sh.member.model.entity.Member;
import com.sh.member.model.entity.Member_del;
import com.sh.member.model.exception.MemberException;

public class MemberDao {

	public int insertMember(Connection conn, Member member) {
		String sql = """
			insert into
					member
			values (?, ?, ?, ?, ?, default, default)
				""";
		int result = 0;
		// 1. PreparedStatement객체생성(sql) - 미완성쿼리 값 대입
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getGender());
			pstmt.setDate(4, member.getBirthday());
			pstmt.setString(5, member.getEmail());
			// 2. 쿼리실행 pstmt.executeUpdate():int
			result = pstmt.executeUpdate();	
			
			
		} catch (SQLException e) {
			// unchecked 예외로 전화후 던지기
			throw new MemberException("회원가입 오류!", e);
			
		}
		
		return result;
	}
	
	public List<Member> findByName(Connection conn, String name) {
		List<Member> members = new ArrayList<>();
		String sql = "select * from member where name like ?";
		// 1. PreparedStatement객체 생성(query)
		//     - 미완성쿼리 값대입
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, "%" + name + "%");
			//  2. 쿼리실행 PreparedStatement#executeQuery(): ResultSet
			try(ResultSet rset = pstmt.executeQuery()){
				//  3. ResultSet 처리
				while(rset.next()) {
					members.add(handleMemberResultSet(rset));
				}
				
			}
			
		} catch (SQLException e) {
			throw new MemberException("회원이름검색 오류!", e);
		}
		return members;
	}

	private Member handleMemberResultSet(ResultSet rset) throws SQLException {
		Member member = new Member();
		member.setId(rset.getString("id"));
		member.setName(rset.getString("name"));
		member.setGender(rset.getString("gender"));
		member.setBirthday(rset.getDate("birthday"));
		member.setEmail(rset.getString("email"));
		member.setPoint(rset.getInt("point"));
		member.setCreatedAt(rset.getTimestamp("created_at"));
		return member;
	}
	
	private Member_del handleMember_delResultSet(ResultSet rset) throws SQLException {
		Member_del member_del = new Member_del();
		member_del.setId(rset.getString("id"));
		member_del.setName(rset.getString("name"));
		member_del.setGender(rset.getString("gender"));
		member_del.setBirthday(rset.getDate("birthday"));
		member_del.setEmail(rset.getString("email"));
		member_del.setPoint(rset.getInt("point"));
		member_del.setCreatedAt(rset.getTimestamp("created_at"));
		member_del.setDelDate(rset.getTimestamp("del_date"));
		return member_del;
	}

	public List<Member> findAll(Connection conn) {
		List<Member> members = new ArrayList<>();
		String sql = "select * from member";
		// 1. PreparedStatement객체 생성(query)
		//     - 미완성쿼리 값대입
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			//  2. 쿼리실행 PreparedStatement#executeQuery(): ResultSet
			try(ResultSet rset = pstmt.executeQuery()){
				//  3. ResultSet 처리
				while(rset.next()) {
					members.add(handleMemberResultSet(rset));
				}
			}
			
		} catch (SQLException e) {
			throw new MemberException("전체 조회 오류!!", e);
		}
		return members;
	}

	public List<Member> findById(Connection conn, String id) {
		List<Member> members = new ArrayList<>();
		String sql = "select * from member where id = ?";
		 //  1. PreparedStatement객체 생성(query)
		 //     - 미완성쿼리 값대입
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			//  2. 쿼리실행 PreparedStatement#executeQuery(): ResultSet
			try(ResultSet rset = pstmt.executeQuery()){
				//  3. ResultSet 처리
				while(rset.next()) {
					members.add(handleMemberResultSet(rset));
				}
			}
		} catch (SQLException e) {
			throw new MemberException("아이디 조회 오류!!", e);
		}
		return members;
	}

	public int deleteMember(Connection conn, String id) {
		int result = 0;
		String sql = "delete from member where id = ? ";
		// 1. PreparedStatement객체생성(sql) - 미완성쿼리 값 대입
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, id);
			// 2. 쿼리실행 pstmt.executeUpdate():int
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new MemberException("회원 탈퇴 오류!", e);
		}
		return result;
	}

	public int updateMemberName(Connection conn, String id, String newName) {
		int result = 0;
		String sql = "update member set name = ? where id = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, newName);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new MemberException("회원 이름 수정 오류!", e);
		}
		return result;
	}

	public int updateMemberBirthday(Connection conn, String id, Date newBirthday) {
		int result = 0;
		String sql = "update member set birthday = ? where id = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setDate(1, newBirthday);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new MemberException("회원 생일 수정 오류!", e);
		}
		return result;
	}

	public int updateMemberEmail(Connection conn, String id, String newEmail) {
		int result = 0;
		String sql = "update member set email = ? where id = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, newEmail);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new MemberException("회원 이메일 수정 오류!", e);
		}
		return result;
	}

	public List<Member_del> findFireAll(Connection conn) {
		List<Member_del> members_del = new ArrayList<>();
		String sql = "select * from member_del";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			try(ResultSet rset = pstmt.executeQuery()){
				while(rset.next()) {
					members_del.add(handleMember_delResultSet(rset));
				}
			}
		} catch (SQLException e) {
			
			throw new MemberException("회원탈퇴 출력 오류!!!", e);
		}
		return members_del;
	}

}
