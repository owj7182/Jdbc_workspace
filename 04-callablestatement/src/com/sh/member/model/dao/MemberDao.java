package com.sh.member.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.sh.member.model.entity.Member;
import com.sh.member.model.exception.MemberException;

import oracle.jdbc.OracleTypes;

public class MemberDao {
	private Properties prop = new Properties();
	
	public MemberDao() {
		try {
			prop.load(new FileReader("resources/member-query.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Member findById(Connection conn, String id) {
		String sql = prop.getProperty("findById"); // {call proc_find_member_by_id(?, ?, ?, ?, ?, ?, ?)}
		Member member = null;
		try (CallableStatement cstmt = conn.prepareCall(sql)) {
			// 미완성 쿼리 값대입 (in/out)
			cstmt.setString(1, id);
			// out모드 매개변수(타입)
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.registerOutParameter(4, Types.DATE);
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.registerOutParameter(6, Types.INTEGER);
			cstmt.registerOutParameter(7, Types.TIMESTAMP);
			
			// 실행
			cstmt.execute();
			
			// Entity객체에 옮겨담기
			member = new Member();
			member.setId(id);
			member.setName(cstmt.getString(2));
			member.setGender(cstmt.getString(3));
			member.setBirthday(cstmt.getDate(4));
			member.setEmail(cstmt.getString(5));
			member.setPoint(cstmt.getInt(6));
			member.setCreatedAt(cstmt.getTimestamp(7));
			
		} catch (SQLException e) {
			if(e.getMessage().contains("ORA-01403")) {} // 해당 데이터 없는 경우 무시
			else
			throw new MemberException("회원 아이디 조회 오류!", e);
		}
		
		return member;
	}

	public List<Member> findAll(Connection conn) {
		List<Member> members = new ArrayList<>();
		String sql = prop.getProperty("findAll");
		
		try (CallableStatement cstmt = conn.prepareCall(sql)) {
			// cursor out모드 매개변수 등록
			// ojdbc.jar에서 제공하는 오라클 커서타입(sys_refcursor)
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			// 실행
			cstmt.execute();
			
			try(ResultSet rset = (ResultSet) cstmt.getObject(1)) {
				while(rset.next())
				members.add(handleMemberResultSet(rset));
			}
			
		} catch (SQLException e) {
			
				throw new MemberException("회원 전체 조회 오류!", e);
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

	public int insertMember(Connection conn, Member member) {
		int result = 0;
		String sql = prop.getProperty("insertMember");
		
		try (CallableStatement cstmt = conn.prepareCall(sql)) {
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setString(2, member.getId());
			cstmt.setString(3, member.getName());
			cstmt.setString(4, member.getGender());
			cstmt.setDate(5, member.getBirthday());
			cstmt.setString(6, member.getEmail());
			
			cstmt.execute();
			
			// out모드 매개변수
			result = cstmt.getInt(1);
			
			
		} catch (SQLException e) {
			throw new MemberException("회원가입 오류!", e);
		}
		return result;
	}
}
