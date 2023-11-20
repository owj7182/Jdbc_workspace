package com.sh.member.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sh.member.model.entity.Member;

public class MemberDao {
	
	private String driverClassname = "oracle.jdbc.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "student";
	private String password = "student";
	
	/**
	 * <pre>
	 * DQL jdbc 절차
	 * 1. Driver클래스 등록
	 * 2. Connection객체 생성(url, id, pw)
	 * 3. PreparedStatement객체 생성(query)
	 * 4. 쿼리실행 - ResultSet객체 반환
	 * 5. ResultSet처리
	 * 6. 반환 (ResultSet - PreparedStatement - Connection)
	 * 
	 * - 여러건 조회하는 경우 : 0행이면 빈 list반환, n행이면 n개의 member객체를 가진 list반환
	 * - 한건 조회하는 경우 : 0행이면 null 반환, 1행이면 member객체 1개 반환
	 * 
	 */
	public List<Member> findAll() {
		List<Member> members = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member order by created_at desc";
		try {
//		 1. Driver클래스 등록
			Class.forName(driverClassname);
//		 2. Connection객체 생성(url, id, pw)
			conn = DriverManager.getConnection(url, user, password);
//		 3. PreparedStatement객체 생성(query)
			pstmt = conn.prepareStatement(sql);
//		 4. 쿼리실행 - ResultSet객체 반환
			rset = pstmt.executeQuery();
//		 5. ResultSet처리
			while(rset.next()) {
				String id = rset.getString("id");
				String name = rset.getString("name");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				int point = rset.getInt("point");
				Timestamp createdAt = rset.getTimestamp("created_at");
				Member member = new Member(id, name, gender, birthday, email, point, createdAt);
				members.add(member);
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();			
		} finally {
			try {
				rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//		 6. 반환 (ResultSet - PreparedStatement - Connection)
		return members;
	}
	
	/**
	 * <pre>
	 * DML jdbc 처리절차
	 * 1. 드라이버클래스 등록
	 * 2. Connection객체 생성(url, user, password)
	 * 	- autocommit false설정
	 * 3. PreparedStatement객체 생성(query)
	 * 	- 미완성 쿼리의 ? 값대입
	 * 4. 쿼리 실행 PreparedStatement#executeUpdate - int반환
	 * 5. 트랜잭션처리 (commit/rollback)
	 * 6. 자원반납
	 */
	public int insertMember(Member member) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = """
				insert into
					member
				values (
					?, ?, ?, ?, ?, default, default
				)
				""";
	try {
		// 1. 드라이버클래스 등록
		Class.forName(driverClassname);
		// 2. Connection객체 생성(url, user, password)
		conn = DriverManager.getConnection(url, user, password);
		// 	- autocommit false설정
		conn.setAutoCommit(false);
		// 3. PreparedStatement객체 생성(query)
		pstmt = conn.prepareStatement(sql);
		// 	- 미완성 쿼리의 ? 값대입
		pstmt.setString(1, member.getId());
		pstmt.setString(2, member.getName());
		pstmt.setString(3, member.getGender());
		pstmt.setDate(4, member.getBirthday());
		pstmt.setString(5, member.getEmail());
		// 4. 쿼리 실행 - int반환
		result = pstmt.executeUpdate(); // 처리된 행의 수 반환
		// 5. 트랜잭션처리 (commit/rollback)
		if (result > 0) conn.commit();
		else conn.rollback();
	} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
	} finally {
		// 6. 자원반납
		try {
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		return result;
	}
//	DQL jdbc 절차
//	 * 1. Driver클래스 등록
//	 * 2. Connection객체 생성(url, id, pw)
//	 * 3. PreparedStatement객체 생성(query)
//	 * 4. 쿼리실행 PreparedStatement#executeQuery()- ResultSet객체 반환
//	 * 5. ResultSet처리
//	 * 6. 반환 (ResultSet - PreparedStatement - Connection)
//	 * 
//	 * - 여러건 조회하는 경우 : 0행이면 빈 list반환(null 아님), n행이면 n개의 member객체를 가진 list반환
//	 * - 한건 조회하는 경우 : 0행이면 null 반환, 1행이면 member객체 1개 반환
	public Member findById(String id) {
		Member member = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member where id = ?";
		
//		 * 1. Driver클래스 등록
		try {
			Class.forName(driverClassname);
//		 * 2. Connection객체 생성(url, id, pw)
			conn = DriverManager.getConnection(url, user, password);
//		 * 3. PreparedStatement객체 생성(query)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
//		 * 4. 쿼리실행 - ResultSet객체 반환
			rset = pstmt.executeQuery();
//		 * 5. ResultSet처리
			while(rset.next()) {
				String name = rset.getString("name");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				int point = rset.getInt("point");
				Timestamp createdAt = rset.getTimestamp("created_at");
				member = new Member(id, name, gender, birthday, email, point, createdAt);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		 * 6. 반환 (ResultSet - PreparedStatement - Connection)
//		 * 
//		 * - 여러건 조회하는 경우 : 0행이면 빈 list반환, n행이면 n개의 member객체를 가진 list반환
//		 * - 한건 조회하는 경우 : 0행이면 null 반환, 1행이면 member객체 1개 반환
		return member;
	}
	/**
	 * <pre>
	 * DQL jdbc 절차
	 * 1. Driver클래스 등록
	 * 2. Connection객체 생성(url, id, pw)
	 * 3. PreparedStatement객체 생성(query)
	 * 4. 쿼리실행 - ResultSet객체 반환
	 * 5. ResultSet처리
	 * 6. 반환 (ResultSet - PreparedStatement - Connection)
	 * 
	 * - 여러건 조회하는 경우 : 0행이면 빈 list반환, n행이면 n개의 member객체를 가진 list반환
	 * - 한건 조회하는 경우 : 0행이면 null 반환, 1행이면 member객체 1개 반환
	 * 
	 * 3. 이름 검색
	 */
	public List<Member> findByName(String _name) {
		List<Member> members = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member where name like ? ";
//		String sql = "select * from member where name like '%' || ? || '%'";
		
		try {
//		* 1. Driver클래스 등록
			Class.forName(driverClassname);
//		* 2. Connection객체 생성(url, id, pw)
			conn = DriverManager.getConnection(url, user, password);
//		* 3. PreparedStatement객체 생성(query)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + _name + "%");
//		* 4. 쿼리실행 - ResultSet객체 반환
			rset = pstmt.executeQuery();
//		* 5. ResultSet처리
			while(rset.next()) {
				String id = rset.getString("id");
				String name = rset.getString("name");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				int point = rset.getInt("point");
				Timestamp createdAt = rset.getTimestamp("created_at");
				Member member = new Member(id, name, gender, birthday, email, point, createdAt);
				members.add(member);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		* 6. 반환 (ResultSet - PreparedStatement - Connection)
		return members;
	}
	/**
	 * <pre>
	 * DML jdbc 처리절차
	 * 1. 드라이버클래스 등록
	 * 2. Connection객체 생성(url, user, password)
	 * 	- autocommit false설정
	 * 3. PreparedStatement객체 생성(query)
	 * 	- 미완성 쿼리의 ? 값대입
	 * 4. 쿼리 실행 PreparedStatement#executeUpdate - int반환
	 * 5. 트랜잭션처리 (commit/rollback)
	 * 6. 자원반납
	 * 
	 * 6. 회원 탈퇴
	 */
	public int deleteMember(String id) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "delete from member where id = ?";
	try {
//		 1. 드라이버클래스 등록
		Class.forName(driverClassname);
//		 2. Connection객체 생성(url, user, password)
		conn = DriverManager.getConnection(url, user, password);
//		 	- autocommit false설정
		conn.setAutoCommit(false);
//		 3. PreparedStatement객체 생성(query)
		pstmt = conn.prepareStatement(sql);
//		 	- 미완성 쿼리의 ? 값대입
		pstmt.setString(1, id);
//		 4. 쿼리 실행 PreparedStatement#executeUpdate - int반환
		result = pstmt.executeUpdate();
//		 5. 트랜잭션처리 (commit/rollback)
		if (result > 0) conn.commit();
		else conn.rollback();
	} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
	} finally {
		try {
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//		 6. 자원반납
		return result;
	}

	public int updateMemberName(String id, String name) {
		int result = 0;
		Member member = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update member set name = ? where id = ?";
		
		try {
			// 1. 드라이버 클래스 등록
			Class.forName(driverClassname);
			// 2. Connection 객체 생성(url, user, password)
			conn = DriverManager.getConnection(url, user, password);
			// 	- autocommit false 설정
			conn.setAutoCommit(false);
			// 3. PreparedStatement 객체 생성(query)
			pstmt = conn.prepareStatement(sql);
			// 	- 미완성 쿼리의 ? 값대입
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			// 4. 쿼리실행 PreparedStatement#executeUpdate() - int 반환
			result = pstmt.executeUpdate();
			// 5. 트랜젝션 처리(commit/rollback)
			if(result > 0 ) conn.commit();
			else conn.rollback();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// 6. 자원반납
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	
	}

	public int updateMemberBirthday(String id, String birthday) {
		int result = 0;
		Member member = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update member set birthday = to_date(?, 'yyyymmdd') where id = ?";
		
		try {
			// 1. 드라이버 클래스 등록
			Class.forName(driverClassname);
			// 2. Connection 객체 생성(url, user, password)
			conn = DriverManager.getConnection(url, user, password);
			// 	- autocommit false 설정
			conn.setAutoCommit(false);
			// 3. PreparedStatement 객체 생성(query)
			pstmt = conn.prepareStatement(sql);
			// 	- 미완성 쿼리의 ? 값대입
			pstmt.setString(1, birthday);
			pstmt.setString(2, id);
			// 4. 쿼리실행 PreparedStatement#executeUpdate() - int 반환
			result = pstmt.executeUpdate();
			// 5. 트랜젝션 처리(commit/rollback)
			if(result > 0 ) conn.commit();
			else conn.rollback();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// 6. 자원반납
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public int updateMemberEmail(String id, String email) {
		int result = 0;
		Member member = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update member set email = ? where id = ?";
		
		try {
			// 1. 드라이버 클래스 등록
			Class.forName(driverClassname);
			// 2. Connection 객체 생성(url, user, password)
			conn = DriverManager.getConnection(url, user, password);
			// 	- autocommit false 설정
			conn.setAutoCommit(false);
			// 3. PreparedStatement 객체 생성(query)
			pstmt = conn.prepareStatement(sql);
			// 	- 미완성 쿼리의 ? 값대입
			pstmt.setString(1, email);
			pstmt.setString(2, id);
			// 4. 쿼리실행 PreparedStatement#executeUpdate() - int 반환
			result = pstmt.executeUpdate();
			// 5. 트랜젝션 처리(commit/rollback)
			if(result > 0 ) conn.commit();
			else conn.rollback();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// 6. 자원반납
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

}
