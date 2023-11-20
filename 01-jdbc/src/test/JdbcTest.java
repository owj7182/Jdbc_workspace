package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTest {
	
	public static void main(String[] args) {
		JdbcTest main = new JdbcTest();
//		main.test1();
		main.test2();
	}
	String driverClassName = "oracle.jdbc.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521"; // 접속프로토콜@url:port 
	String user = "student";
	String password = "student";
	/**
	 * <pre>
	 * jdbc 절차
	 * 1. Driver클래스 등록
	 * 2. Connection객체 생성(url, id, pw)
	 * 3. PreparedStatement객체 생성(query)
	 * 4. 쿼리실행 - ResultSet객체 반환
	 * 5. ResultSet처리
	 * 6. 반환 (ResultSet - PreparedStatement - Connection)
	 */
	private void test2() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			// 1. Driver클래스 등록
			Class.forName(driverClassName);
			System.out.println("1.드라이버클래스 등록 완료!");
			
			// 2. Connection
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("2. Connection객체생성 완료!");
			
			// 3. PreparedStatement객체 생성
			pstmt = conn.prepareStatement("select * from member");
			System.out.println("3. PreparedStatement 객체생성 완료!");
			
			// 4. 쿼리실행 및 ResulSet 반환
			rset = pstmt.executeQuery();
			System.out.println("4. 쿼리실행 완료!");
			
			// 5. ResultSet 처리
			while(rset.next()) {
				String id = rset.getString("id");
				String name = rset.getString("name");
				System.out.println(id + " " + name);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			// 6. 자원반납
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
	}
	
	/**
	 * ojdbc8.jar 연결테스트
	 */
	private void test1() {
		// 클래스 등록
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("ojdbc연결 완료!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
