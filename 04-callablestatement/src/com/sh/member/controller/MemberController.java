package com.sh.member.controller;

import java.util.List;

import com.sh.member.model.entity.Member;
import com.sh.member.model.service.MemberService;

public class MemberController {
	private MemberService memberService = new MemberService();

	public Member findById(String id) {
		Member member = null;
		try {
			member = memberService.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다 : " + e.getMessage());
		}
		
		return member;
	}

	public List<Member> findAll() {
		List<Member> members = null;
		try {
			members = memberService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다 : " + e.getMessage());
		}
		return members;
	}

	public int insertMember(Member member) {
		int result = 0;
		try {
			result = memberService.insertMember(member);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다 : " + e.getMessage());
		}
		
		return result;
	}
}
