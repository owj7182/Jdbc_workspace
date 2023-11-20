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
		return memberService.insertMember(member);
	}

	public List<Member> finByName(String name) {
		return memberService.findByName(name);
	}

	public List<Member> findAll() {
		return memberService.findAll();
	}

	public List<Member> findById(String id) {
		return memberService.findById(id);
	}

	public int deleteMember(String id) {
		return memberService.deleteMember(id);
	}

	public int updateMemberName(String id, String newName) {
		return memberService.updateMemberName(id, newName);
	}

	public int updateMemberBirthday(String id, Date newBirthday) {
		return memberService.updateMemberBirthday(id, newBirthday);
	}

	public int updateMemberEmail(String id, String newEmail) {
		return memberService.updateMemberEmail(id, newEmail);
	}

	public List<Member_del> findFireAll() {
		return memberService.findFireAll();
	}
}
