package com.sh.member.model.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Member_del {
	private String id;
	private String name;
	private String gender;
	private Date birthday;
	private String email;
	private int point;
	private Timestamp createdAt;
	private Timestamp delDate;
	
	public Member_del() {
		super();
	}

	public Member_del(String id, String name, String gender, Date birthday, String email, int point,
			Timestamp createdAt, Timestamp delDate) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.email = email;
		this.point = point;
		this.createdAt = createdAt;
		this.delDate = delDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getDelDate() {
		return delDate;
	}

	public void setDelDate(Timestamp delDate) {
		this.delDate = delDate;
	}

	@Override
	public String toString() {
		return "Member_del [id=" + id + ", name=" + name + ", gender=" + gender + ", birthday=" + birthday + ", email="
				+ email + ", point=" + point + ", createdAt=" + createdAt + ", delDate=" + delDate + "]";
	}
	
	
}
