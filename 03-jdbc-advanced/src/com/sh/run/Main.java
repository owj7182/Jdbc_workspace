package com.sh.run;

import com.sh.member.view.MemberMenu;

/**
 * <pre>
 * MVC패턴
 * - 디자인 패턴(문제해결/기능제공에 있어 코드를 성격별로 나누어 클래스 구조를 만드는 방법) 중 하나
 * - Model
 * 		- Service 업무 로직 담당, 트랜잭션 처리 담당
 * 		- Dao (Data Access Object) db접속 후 쿼리 실행
 * 		- Entity(Vo) db테이블과 매칭
 * 		- ...
 * - View 사용자 인터페이스 담당
 * 		- 메뉴 제공
 * 		- 사용자 입력값 처리
 * 		- 결과출력
 * - Controller 전체 제어의 역할
 * 		- view단으로부터의 모든 요청을 받아들이고, 하위 클래스로 분배
 * 		- 처리 결과를 다시 view단으로 전달
 * 		- 기능 제공주 오류 발생시 분기처리 (사용자 오류메시지 전송) - 모든 예외는 controller까지 던져져야 한다.
 * 		- 예외로그
 *
 */

public class Main {

	public static void main(String[] args) {
		new MemberMenu().mainMenu();
		
		System.out.println("====== 이용해 주셔서 감사합니다 ======");
	}

}
