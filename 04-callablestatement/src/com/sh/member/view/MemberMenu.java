package com.sh.member.view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import com.sh.member.controller.MemberController;
import com.sh.member.model.entity.Member;

public class MemberMenu {
	private Scanner sc = new Scanner(System.in);
	private MemberController memberController = new MemberController();
	
	public void mainMenu() {
		String menu = """
            ===== 회원 관리 프로그램4 =====
            1. 전체조회
            2. 아이디 조회
            3. 회원가입
            0. 프로그램 종료
            ==========================    
            선택 : """;
		while(true) {
			System.out.println();
			System.out.print(menu);
			
			String choice = sc.next();
			
			switch (choice) {
			case "1": 
				displayMembers(memberController.findAll());
				break;
			case "2": 
				displayMember(memberController.findById(inputId()));
				break;
			case "3":
				displayResult("회원가입", memberController.insertMember(inputMember()));
				break;
			case "0": return;
			default:
				System.out.println("> 잘못 입력하셨습니다.");
				break;
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
     * n건의 회원조회 결과를 출력
     * @param members
     */
    private void displayMembers(List<Member> members) {
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.printf("%-10s%-10s%-10s%-10s%-20s%-10s%-20s\n", 
                "ID", "Name", "Gender", "Birthday", "Email", "Point", "CreatedAt");
        System.out.println("--------------------------------------------------------------------------------------");
        if(members == null || members.isEmpty()) {
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
    

	private void displayMember(Member member) {
		if(member == null) {
			System.out.println("> 조회된 회원이 없습니다.");
		}
		else {
			System.out.println("---------------------------------");
			System.out.printf("ID : %s\n", member.getId());
			System.out.printf("Name : %s\n", member.getName());
			System.out.printf("Gender : %s\n", member.getGender().substring(0, 1));
			System.out.printf("Birthday : %s\n", member.getBirthday());
			System.out.printf("Email : %s\n", member.getEmail());
			System.out.printf("Point : %s\n", member.getPoint());
			System.out.printf("CreatedAt : %s\n", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(member.getPoint()));			
			System.out.println("---------------------------------");
		}
	}
	
	/**
	 * 회원가입시 사용자 입력정보를 Member객체로 반환하는 메소드
	 * @return
	 */
	private Member inputMember() {
		Member member = new Member();
		System.out.println("> 회원정보를 입력하세요.");
        for (;;) {
            System.out.print("> 아이디 : ");
            member.setId(sc.next());
            if (memberController.findById(member.getId()) == null) {
                System.out.println("> 사용가능한 아이디입니다 :)");
                break;
            } else {
                System.out.println("> 사용불가능한 아이디입니다. 다시 입력하세요.");
                continue;
            }
        }
		
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
	
	/**
     * DML 처리결과 메소드
     */
    private void displayResult(String type, int result) {
        if(result > 0)
            System.out.println("🎉🎉 " + type + " 성공! 🎉🎉");
        else
            System.out.println("😭😭 " + type + " 실패! 😭😭");                
    }
	

}
