package com.FCI.SWE.Models;

public class UsersInMessage {

	private long group_id;
	private long user_id;
	private long id;

	public UsersInMessage(long group_id, long user_id) {
		this.group_id = group_id;
		this.user_id = user_id;
	}

	public long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
