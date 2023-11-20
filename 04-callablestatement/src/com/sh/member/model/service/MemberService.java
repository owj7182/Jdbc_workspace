package com.sh.member.model.service;

import static com.sh.common.JdbcTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.sh.member.model.dao.MemberDao;
import com.sh.member.model.entity.Member;

public class MemberService {
	private MemberDao memberDao = new MemberDao();

	public Member findById(String id) {
		Connection conn = getConnection();
		
		Member member = memberDao.findById(conn, id);
		
		close(conn);
		
		return member;
	}

	public List<Member> findAll() {
		Connection conn = getConnection();
		
		List<Member> members = memberDao.findAll(conn);
		
		close(conn);
		
		return members;
		
	}
	/**
	 * 프로시져 안에 트랜잭션 처리가 되어있다.
	 * @param member
	 * @return
	 */
	public int insertMember(Member member) {
		Connection conn = getConnection();
		int result = memberDao.insertMember(conn, member);
		close(conn);
		return result;
	}
}
