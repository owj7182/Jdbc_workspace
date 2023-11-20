package com.sh.member.view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import com.sh.member.controller.MemberController;
import com.sh.member.model.entity.Member;
import com.sh.member.model.entity.Member_del;

public class MemberMenu {
	private Scanner sc = new Scanner(System.in);
	private MemberController memberController = new MemberController();
	
	public void mainMenu() {
		String menu = """
	            ===== 회원 관리 프로그램2 =====
	            1. 전체조회
	            2. 아이디 조회
	            3. 이름 검색
	            4. 회원 가입
	            5. 회원정보 변경
	            6. 회원탈퇴
	            7. 탈퇴회원 조회
	            0. 프로그램 종료
	            ==========================    
	            선택 : """;
		while (true) {
			System.out.println();
			System.out.print(menu);
			String choice = sc.next();
			
			List<Member_del> members_del = null;
			List<Member> members = null;
			Member member = null;
			int result = 0;
			String id = null;
			String name = null;
			
			switch (choice) {
			case "1": 
				members = memberController.findAll();
				displayMembers(members);
				break;
			case "2": 
				id = inputId();
				members = memberController.findById(id);
				displayMembers(members);
				break;
			case "3": 
				name = inputName();
				members = memberController.finByName(name);
				displayMembers(members);
				break;
			case "4": 
				member = inputMember();
				result = memberController.insertMember(member);
				displayResult("회원가입", result);
				
				break;
			case "5": 
				updateMenu();
				break;
			case "6": 
				id = inputId();
				result = memberController.deleteMember(id);
				displayResult("회원탈퇴", result);
				break;
			case "7":
				members_del = memberController.findFireAll();
				displayMember_del(members_del);
				break;
			case "0": return;

			default:
				System.out.println("잘못 입력하셨습니다.");
				break;
			}
			
		}
	}
	/**
	 * 회원정보 변경
	 */
	private void updateMenu() {
		
		String menu = """
				+++++++++ 회원 정보수정 +++++++++
				1. 이름수정
				2. 생일수정
				3. 이메일수정
				0. 메인메뉴로 돌아가기
				++++++++++++++++++++++++++++++
				선택 : """;
		String id = inputId();
		List<Member> members = null;
		
		while(true) {
			members = memberController.findById(id);
			displayMembers(members);
			if (members == null) 
				return;
			System.out.print(menu);
			String choice = sc.next();
			int result = 0;
			
			switch(choice) {
			case "1": 
				System.out.print("변경할 이름 : ");
				String newName = sc.next();
				result = memberController.updateMemberName(id, newName);
				System.out.println("회원명 수정 성공!");
				break;
			case "2": 
				System.out.print("변경할 생일 : ");
				Date newBirthday = Date.valueOf(sc.next());
				result = memberController.updateMemberBirthday(id, newBirthday);
				System.out.println("생일 수정 성공!");
				break;
			case "3": 
				System.out.print("변경할 이메일");
				String newEmail = sc.next();
				result = memberController.updateMemberEmail(id, newEmail);
				System.out.println("이메일 수정 성공!");
				break;
				
			case "0": return;
			default: System.out.println("> 잘못입력하셨습니다.");
			
			}
			
		}
		
	}
	/**
	 * 사용자 아이디 입력
	 * @return
	 */
	private String inputId() {
		System.out.print("> 아이디 입력 : ");
		return sc.next();
	}

	/**
	 * 사용자 이름 입력
	 * @return
	 */
	private String inputName() {
		System.out.print(" > 이름 입력 : ");
		return sc.next();
	}

	/**
     * n건의 회원조회 결과를 출력
     * @param members
     */
    private void displayMembers(List<Member> members) {
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.printf("%-10s%-10s%-10s%-10s%-20s%-10s%-20s\n", 
                "ID", "Name", "Gender", "Birthday", "Email", "Point", "CreatedAt");
        System.out.println("--------------------------------------------------------------------------------------");
        if(members.isEmpty()) {
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
        System.out.println("--------------------------------------------------------------------------------------");
    }
    
    /**
     * n건의 회원조회 결과를 출력
     * @param members
     */
    private void displayMember_del(List<Member_del> members_del) {
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.printf("%-10s%-10s%-10s%-10s%-20s%-10s%-20s\n", 
                "ID", "Name", "Gender", "Birthday", "Email", "Point", "CreatedAt");
        System.out.println("--------------------------------------------------------------------------------------");
        if(members_del.isEmpty()) {
            System.out.println("\t\t 조회된 결과가 없습니다.");
        }
        else {
            for(Member_del member_del : members_del) {
                System.out.printf("%-10s%-10s%-10s%-10s%-20s%-10s%-20s%-20s\n", 
                        member_del.getId(), 
                        member_del.getName(), 
                        member_del.getGender(), 
                        member_del.getBirthday(), 
                        member_del.getEmail(), 
                        member_del.getPoint(), 
                        new SimpleDateFormat("yyyy-MM-dd HH:mm").format(member_del.getCreatedAt()),
                		new SimpleDateFormat("yyyy-MM-dd HH:mm").format(member_del.getDelDate()));
            }
        }
        System.out.println("--------------------------------------------------------------------------------------");
    }
    
	/**
     * DML 처리결과 메소드
     */
    private void displayResult(String type, int result) {
        if(result > 0)
            System.out.println("🎉🎉 " + type + " 성공! 🎉🎉");
        else
            System.out.println("😭😭 " + type + " 실패! 😭😭");                
    }
	
	/**
	 * 회원가입시 사용자 입력정보를 Member객체로 반환하는 메소드
	 * @return
	 */
	private Member inputMember() {
		Member member = new Member();
		System.out.println("> 회원정보를 입력하세요.");
		System.out.print("> 아이디 : ");
		member.setId(sc.next());
		System.out.print("> 이름 : ");
		member.setName(sc.next());
		System.out.print("> 성별(M/F) : ");
		member.setGender(sc.next());
		System.out.print("> 생일(ex 1998-03-23) : ");
		member.setBirthday(Date.valueOf(sc.next()));
		System.out.print("> 이메일 : ");
		member.setEmail(sc.next());
		return member;
	}

}
