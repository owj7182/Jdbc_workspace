package com.sh.tb_user.model.service;

import static com.sh.common.JdbcTemplate.*;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import com.sh.tb_user.model.dao.Tb_userDao;
import com.sh.tb_user.model.entity.Tb_user;

public class Tb_userService {
	private Tb_userDao userDao = new Tb_userDao();

	public List<Tb_user> findById(String id) {
		Connection conn = getConnection();
		List<Tb_user> users = userDao.findById(conn, id);
		close(conn);
		return users;
	}

	public int insertUser(Tb_user user) {
		int result = 0;
		Connection conn = getConnection();
		
		try {
			result = userDao.insertUser(conn, user);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		
		return result;
	}

	public int deleteUser(String id) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = userDao.deleteUser(conn, id);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

	public int updateUserName(String id, String newName) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = userDao.updateUserName(conn, id, newName);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

	public int updateUserBirthday(String id, Date newBirthday) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = userDao.updateUserBirthday(conn, id, newBirthday);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

	public int updateUserPhone(String id, String newPhone) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = userDao.updateUserPhone(conn, id, newPhone);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

	public int updateUserEmail(String id, String newEmail) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = userDao.updateUserEmail(conn, id, newEmail);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

	public int updateUserFavoriteGenre(String id, String newFavoriteGenre) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = userDao.updateUserFavoriteGenre(conn, id, newFavoriteGenre);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}
}
