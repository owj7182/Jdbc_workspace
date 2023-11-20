package com.sh.member.controller;

import java.util.List;

import com.sh.member.model.dao.MemberDao;
import com.sh.member.model.entity.Member;

public class MemberController {
	
	private MemberDao memberDao = new MemberDao();
	
	public List<Member> findAll() {
		// dao 요청
		List<Member> members = memberDao.findAll();
		// 처리결과 view단 전달
		return memberDao.findAll();
	}

	public int insertMember(Member member) {
		// dao 요청
		int result = memberDao.insertMember(member);
		// 결과를 view단 전달
		return result;
	}

	public Member findById(String id) {
		
		return memberDao.findById(id);
	}

	public List<Member> findByName(String _name) {
		List<Member> members = memberDao.findByName(_name);
		return memberDao.findByName(_name);
	}

	public int deleteMember(String id) {
		int result = memberDao.deleteMember(id);
		return result;
	}

	public int updateMemberName(String id, String name) {
		return memberDao.updateMemberName(id, name);
	}

	public int updateMemberBirthday(String id, String birthday) {
		return memberDao.updateMemberBirthday(id, birthday);
	}

	public int updateMemberEmail(String id, String email) {
		return memberDao.updateMemberEmail(id, email);
	}

	
	

}
