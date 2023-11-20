package com.sh.run;

import com.sh.member.view.MemberMenu;

/**
 * <pre>
 * MVC패턴
 * 기능을 제공함에 있어 코드가 가진 성격별로 클래스를 나누어 처리하는 패턴
 * - Model
 * 		- entity클래스 : db테이블과 상응하는 클래스
 * 		- dao클래스 : Data Access Object db접근 클래스
 * 		- service클래스 : 업무로직 담당클래스(트랜잭션 처리)
 * 		- ...
 * - View 클래스 : 사용자가 보게될 화면담당. 메뉴/사용자 입력값처리등
 * - Controller 클래스 : 사용자 요청을 처리하는데 있어 전체 제어의 역할.
 * 			- 요청을 받아 하위 클래스에 분배. 처리 결과를 다시 view단 전달
 *
 */
public class Main {

	public static void main(String[] args) {
		new MemberMenu().mainMenu();
		
		System.out.println("===== 이용해 주셔서 감사합니다. ======");
	}

}
