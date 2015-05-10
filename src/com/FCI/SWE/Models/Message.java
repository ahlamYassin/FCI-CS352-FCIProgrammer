package com.FCI.SWE.Models;

public class Message {

	private long id;
	private long from;
	private long to;
	private long group_id;
	private String subject;
	private String body;
	/**
	 * @param id
	 * @param from
	 * @param to
	 * @param subject
	 * @param body
	 */
	public Message(long from, long to, String subject, String body) {
		super();
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
	
	/**
	 * @param from
	 * @param to
	 * @param group_id
	 * @param subject
	 * @param body
	 */
	public Message(long from, long to, long group_id, String subject,
			String body) {
		super();
		this.from = from;
		this.to = to;
		this.group_id = group_id;
		this.subject = subject;
		this.body = body;
	}

	/**
	 * @return the group_id
	 */
	public long getGroup_id() {
		return group_id;
	}

	/**
	 * @param group_id the group_id to set
	 */
	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the from
	 */
	public long getFrom() {
		return from;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(long from) {
		this.from = from;
	}
	/**
	 * @return the to
	 */
	public long getTo() {
		return to;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(long to) {
		this.to = to;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}
	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}
	
	
	
	
}
