package com.sh.address.model.entity;

import java.sql.Date;

public class Address {
	private int id;
	private String memberId;
	private String address;
	private boolean defaultAddr;// 1/0 -> true/false
	private Date createdAt;
	
	public Address() {
		super();
	}

	public Address(int id, String memberId, String address, boolean defaultAddr, Date createdAt) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.address = address;
		this.defaultAddr = defaultAddr;
		this.createdAt = createdAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isDefaultAddr() {
		return defaultAddr;
	}

	public void setDefaultAddr(boolean defaultAddr) {
		this.defaultAddr = defaultAddr;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", memberId=" + memberId + ", address=" + address + ", defaultAddr=" + defaultAddr
				+ ", createdAt=" + createdAt + "]";
	}
	
	
}
