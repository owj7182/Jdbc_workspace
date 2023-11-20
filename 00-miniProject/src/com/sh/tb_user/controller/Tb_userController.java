package com.sh.tb_user.controller;

import java.sql.Date;
import java.util.List;

import com.sh.tb_user.model.entity.Tb_user;
import com.sh.tb_user.model.service.Tb_userService;

public class Tb_userController {
	private Tb_userService userService = new Tb_userService();

	public List<Tb_user> findById(String id) {
		List<Tb_user> users = null;
		try {
			users = userService.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		
		return users;
	}

	public int insertUser(Tb_user user) {
		int result = 0;
		try {
			result = userService.insertUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다" + e.getMessage());
		}
		return result;
	}

	public int deleteUser(String id) {
		int result = 0;
		try {
			result = userService.deleteUser(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다" + e.getMessage());
		}
		return result;
	}

	public int updateUserName(String id, String newName) {
		int result = 0;
		try {
			result = userService.updateUserName(id, newName);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다" + e.getMessage());
		}
		return result;
	}

	public int updateUserBirthday(String id, Date newBirthday) {
		int result = 0;
		try {
			result = userService.updateUserBirthday(id, newBirthday);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다" + e.getMessage());
		}
		return result;
	}

	public int updateUserPhone(String id, String newPhone) {
		int result = 0;
		try {
			result = userService.updateUserPhone(id, newPhone);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다" + e.getMessage());
		}
		return result;
	}

	public int updateUserEmail(String id, String newEmail) {
		int result = 0;
		try {
			result = userService.updateUserEmail(id, newEmail);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다" + e.getMessage());
		}
		return result;
	}

	public int updateUserFavoriteGenre(String id, String newFavoriteGenre) {
		int result = 0;
		try {
			result = userService.updateUserFavoriteGenre(id, newFavoriteGenre);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다" + e.getMessage());
		}
		return result;
	}
}
