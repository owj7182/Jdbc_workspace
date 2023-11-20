package com.sh.member.controller;

import java.sql.Date;
import java.util.List;

import com.sh.member.model.entity.Member;
import com.sh.member.model.entity.Member_del;
import com.sh.member.model.service.MemberService;
/**
 *  controller에서는 업무로직담당 service에게 업무를 위임.
 *
 */
public class MemberController {
	private MemberService memberService = new MemberService();

	public int insertMember(Member member) {
		int result = 0;
		try {
			result = memberService.insertMember(member);
		} catch (Exception e) {
			// 1. 예외로그
			e.printStackTrace();
			// 2. 오류메시지 전송
			System.err.println("> 불편을 드려 죄송합니다." + e.getMessage());
		}
		return result;
	}

	public List<Member> finByName(String name) {
		List<Member> members = null;
		try {
			members = memberService.findByName(name);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return members;
	}

	public List<Member> findAll() {
		List<Member> members = null;
		try {
			members = memberService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		
		return members;
	}

	public List<Member> findById(String id) {
		List<Member> members = null;
		try {
			members = memberService.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return members;
	}

	public int deleteMember(String id) {
		int result = 0;
		try {
			result = memberService.deleteMember(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return result;
	}

	public int updateMemberName(String id, String newName) {
		int result = 0;
		try {
			result = memberService.updateMemberName(id, newName);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return result;
	}

	public int updateMemberBirthday(String id, Date newBirthday) {
		int result = 0;
		try {
			result = memberService.updateMemberBirthday(id, newBirthday);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다, : " + e.getMessage());
		}
		return result;
	}

	public int updateMemberEmail(String id, String newEmail) {
		int result = 0;
		try {
			result = memberService.updateMemberEmail(id, newEmail);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return result;
	}

	public List<Member_del> findFireAll() {
		List<Member_del> members_del = null;
		try {
			members_del = memberService.findFireAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return members_del;
	}

}
