package com.sh.member.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.sh.member.controller.MemberController;
import com.sh.member.model.entity.Member;

public class MemberMenu {
	private Scanner sc = new Scanner(System.in);
	private MemberController memberController = new MemberController();
	
	public void mainMenu() {
		String menu = """
				====== 회원관리 프로그램 =====
				1. 전체 조회
				2. 아이디 조회
				3. 이름 검색
				4. 회원 가입
				5. 회원정보 변경
				6. 회원 탈퇴
				0. 프로그램 종료
				=========================
				선택 : """;
		
		while(true) {
			System.out.println();
			System.out.print(menu);
			String choice = sc.next();
			Member member = null;
			int result = 0;
			List<Member> members = null;
			String id = null;
			String name = null;
			
			switch (choice) {
			case "1": 
				 members = memberController.findAll(); 
				displayMembers(members);
				break;
			case "2": 
				id = inputId();
				member = memberController.findById(id);
				displayMember(member);
				break;
			case "3": 
				name = inputName();
				members = memberController.findByName(name);
				displayMembers(members);
				break;
			case "4": 
				member = inputMember(); // 회원정보 입력
				result = memberController.insertMember(member); // db저장 요청
				displayResult("회원가입", result); // 결과 출력
				break;
			case "5": 
				id = inputId();
				member = memberController.findById(id);
				if (member == null)
					mainMenu();
				displayMember(member);
				updateMenu(id);
				break;
			case "6": 
				id = inputId();
				result = memberController.deleteMember(id);
				displayResult("회원탈퇴.", result);
				break;
			case "0": return; // 현재 메소드를 호출한 곳으로 리턴
			default: 
				System.out.println("잘못 입력하셨습니다.");
				break;
			}
		}
		
	}
	private void updateMenu(String id) {
		int result = 0;
		String menu = """
				+++++++++ 회원 정보수정 +++++++++
				1. 이름수정
				2. 생일수정
				3. 이메일수정
				0. 메인메뉴로 돌아가기
				++++++++++++++++++++++++++++++
				선택 : """;
		
		while(true) {
			System.out.print(menu);
			String choice = sc.next();
			switch (choice) {
			case "1":
				System.out.print(">변경할 이름 : ");
				result = memberController.updateMemberName(id, sc.next());
				displayResult("이름 수정", result);
				break;
			case "2":
				System.out.print("> 변경할 생일(ex.19980323) : ");
				result = memberController.updateMemberBirthday(id, sc.next());
				displayResult("생일 수정", result);
				break;
			case "3":
				System.out.print("변경할 이메일 : ");
				result = memberController.updateMemberEmail(id, sc.next());
				displayResult("이메일 수정", result);
				break;
			case "0":
				return;
			default:
				System.out.println("잘못 입력하셨씁니다.");
						
			}
			Member member = memberController.findById(id);
			displayMember(member);
		}
	}
	/**
	 * 조회할 이름 입력
	 * @return
	 */
	private String inputName() {
		System.out.print("> 이름 : ");
		return sc.next();
	}
	/**
	 * 1건의 회원정보 조회
	 */
	private void displayMember(Member member) {
		if(member == null) {
			System.out.println("> 조회된 회원이 없습니다.");
		}
		else {
			System.out.println("---------------------------------");
			System.out.printf("ID : %s\n", member.getId());
			System.out.printf("Name : %s\n", member.getName());
			System.out.printf("Gender : %s\n", member.getGender());
			System.out.printf("Birthday : %s\n", member.getBirthday());
			System.out.printf("Email : %s\n", member.getEmail());
			System.out.printf("Point : %s\n", member.getPoint());
			System.out.printf("CreatedAt : %s\n", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(member.getPoint()));			
			System.out.println("---------------------------------");
		}
	}
	/**
	 * 조회할 아이디 입력
	 */
	private String inputId() {
		System.out.print("> 아이디 : ");
		return sc.next();
	}
	
	/**
	 * n건의 회원조회 결과를 출력
	 */
	private void displayMembers(List<Member> members) {
		System.out.println("----------------------------------------------------------------------------------------------");
		System.out.printf("%-10s%-10s%-10s%-10s%-20s%-10s%-20s\n", 
				"ID", "Name", "Gender", "Birthday", "Email", "Point", "CreatedAt");
		System.out.println("----------------------------------------------------------------------------------------------");
		if (members.isEmpty()) {
			System.out.println("\t\t 조회된 결과가 없습니다.");
		}
		else {
			for(Member member : members) {
				System.out.printf("%-10s%-10s%-10s%-10s%-20s%-10s%-20s\n",
						member.getId(),
						member.getName(),
						member.getGender(),
						member.getBirthday(),
						member.getEmail(),
						member.getPoint(),
						new SimpleDateFormat("yyyy-MM-dd HH:mm").format(member.getCreatedAt()));
			}
			
		}
	}
	
	/**
	 * DML 처리결과 메소드 
	 */
	private void displayResult(String type, int result) {
		if (result > 0)
			System.out.println(type + " 성공!! ");
		else
			System.out.println(type + " 실패ㅠㅠ");
	}

	/**
	 * 회원가입 메소드
	 * - 회원정보를 입력받아 Member객체로 반환
	 */
	private Member inputMember() {
		System.out.println("> 회원 정보를 입력하세요.");
		System.out.print("> 아이디 : ");
		String id = sc.next();
		System.out.print("> 이름 : ");
		String name = sc.next();
		System.out.print("> 성별(M/F) : ");
		String gender = sc.next();
		System.out.print("> 생일(19980323) : ");
		String _birthday = sc.next();
		Date birthday = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			// java.util.Date -> long -> java.sql.Date
			birthday = new Date(sdf.parse(_birthday).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.print("> 이메일 : ");
		String email = sc.next();
		return new Member(id, name, gender, birthday, email, 0, null);
	}
}
