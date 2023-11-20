package com.sh.member.model.service;

// static자원(field/method) import
import static com.sh.common.JdbcTemplate.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.sh.common.JdbcTemplate;
import com.sh.member.model.dao.MemberDao;
import com.sh.member.model.entity.Member;
import com.sh.member.model.entity.Member_del;

/**
 * <pre>
 * DML처리절차
 * 1. 드라이버클래스 등록(최초 1회)
 * 2. Connection생성(url, user, password) - autoCommit false설정
 * 3. PreparedStatement객체생성(sql) - 미완성쿼리 값 대입
 * 4. 쿼리실행 pstmt.executeUpdate():int
 * 5. 트랜잭션 처리 commit/rollback
 * 6. 자원반환
 * 
 * Service단
 * 1. Connection생성(url, user, password) - autoCommit false설정
 * 2. Dao 요청
 * 3. 트랜잭션 처리 commit/rollback
 * 4. 자원 반납(conn)
 * 
 * Dao단
 * 1. PreparedStatement객체생성(sql) - 미완성쿼리 값 대입
 * 2. 쿼리실행 pstmt.executeUpdate():int
 * 3. 자원 반납(pstmt)
 * 
 * 
 * 
 * <pre>
 * DQL jdbc 처리절차
 * 1. Driver클래스 등록
 * 2. Connection객체 생성(url, id, pw)
 * 3. PreparedStatement객체 생성(query)
 *     - 미완성쿼리 값대입
 * 4. 쿼리실행 PreparedStatement#executeQuery(): ResultSet
 * 5. ResultSet 처리
 * 6. 반환 (ResultSet - PreparedStatment - Connection)
 * 
 * - Service단
 * 	1. Connection객체 생성
 *  2. Dao호출
 *  3. 반환(conn)
 * 
 * - Dao단
 *  1. PreparedStatement객체 생성(query)
 *     - 미완성쿼리 값대입
 *  2. 쿼리실행 PreparedStatement#executeQuery(): ResultSet
 *  3. ResultSet 처리
 *  4. 반환(rset, pstmt)
 * 
 */
 
public class MemberService {
	private MemberDao memberDao = new MemberDao();
	
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "student";
	String password = "student";
	
	
	public int insertMember(Member member) {
		int result = 0;
//		1. Connection생성(url, user, password) - autoCommit false설정
		Connection conn = getConnection();
		try {
//			2. Dao요청
			result = memberDao.insertMember(conn, member);
//			3. 트랜잭션 처리 commit/rollback
			commit(conn);
			
		} catch (Exception e) {
			rollback(conn);
			e.printStackTrace();
		} finally {
//			4. 자원 반납(conn)
			close(conn);
		}
		return result;
	}

	
	public List<Member> findByName(String name) {
		// 1. Connection객체 생성
		Connection conn = getConnection();
		// 2. Dao호출
		List<Member> members = memberDao.findByName(conn, name);
		// 3. 반환(conn)
		close(conn);
		return members;
	}


	public List<Member> findAll() {
		// Connection객체 생성
		Connection conn = getConnection();
		//  2. Dao호출
		List<Member> members = memberDao.findAll(conn);
		//  3. 반환(conn)
		close(conn);
		
		return members;
	}


	public List<Member> findById(String id) {
		//  1. Connection객체 생성
		Connection conn = getConnection();
		//  2. Dao호출
		List<Member> members = memberDao.findById(conn, id);
		//  3. 반환(conn)
		close(conn);
		return members;
	}


	public int deleteMember(String id) {
		int result = 0;
		// 1. Connection객체 생성
		Connection conn = getConnection();
		try {
			// 2. Dao 요청
			result = memberDao.deleteMember(conn, id);
			// 3. 트랜잭션 처리 commit/rollback
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			e.printStackTrace();
		} finally {
			// 4. 자원 반납(conn)
			close(conn);
		}
		return result;
	}


	public int updateMemberName(String id, String newName) {
		int result = 0;
		
		Connection conn = getConnection();
		
		result = memberDao.updateMemberName(conn, id, newName);
		
		if(result >0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
			close(conn);
		return result;
	}


	public int updateMemberBirthday(String id, Date newBirthday) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = memberDao.updateMemberBirthday(conn, id, newBirthday);
			if (result > 0)
				commit(conn);
		} catch (Exception e) {
			rollback(conn);
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return result;
	}


	public int updateMemberEmail(String id, String newEmail) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = memberDao.updateMemberEmail(conn, id, newEmail);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return result;
	}


	public List<Member_del> findFireAll() {
		//  1. Connection객체 생성
		Connection conn = getConnection();
		//  2. Dao호출
		List<Member_del> members_del = memberDao.findFireAll(conn);
		//  3. 반환(conn)
		close(conn);
		return members_del;
	}
}
