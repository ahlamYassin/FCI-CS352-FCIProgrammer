package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.List;

import com.FCI.SWE.Interfaces.Observer;
import com.FCI.SWE.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class MessageEntity {

	private long from;
	private long to = 0;
	private String subject;
	private String body;
	private long id;
	private long group_id = 0;

	/**
	 * @param from
	 * @param to
	 * @param subject
	 * @param body
	 */
	public MessageEntity(long from, String subject, String body, long to) {
		super();
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.body = body;
	}

	/**
	 * @param from
	 * @param to
	 * @param subject
	 * @param body
	 * @param group_id
	 */
	public MessageEntity(long from, long to, String subject, String body,
			long group_id) {
		super();
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.body = body;
		this.group_id = group_id;
	}

	/**
	 * @return the group_id
	 */
	public long getGroup_id() {
		return group_id;
	}

	/**
	 * @param group_id
	 *            the group_id to set
	 */
	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}

	/**
	 * @return the from
	 */
	public long getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
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
	 * @param to
	 *            the to to set
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
	 * @param subject
	 *            the subject to set
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
	 * @param body
	 *            the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * method to get an id list for all users in a group message
	 * 
	 * @param group_id
	 * @return
	 */
	public static List<Observer> usersInAGroup(long group_id) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("messagas");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Observer> observers = new ArrayList<>();
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("group_id").toString().equals(group_id)) {
				UserEntity observer = UserEntity.getUser(Long.parseLong(entity
						.getProperty("to").toString()));
				observers.add(observer);
			}
		}
		return observers;
	}

	/**
	 * method to get all messages archive for a single group
	 * 
	 * @param group_id
	 * @return
	 */
	public static List<MessageEntity> getByGroup(long group_id) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("messagas");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<MessageEntity> messages = new ArrayList<>();
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("group_id").toString().equals(group_id)) {
				MessageEntity message = new MessageEntity(Long.parseLong(entity
						.getProperty("from").toString()), Long.parseLong(entity
						.getProperty("to").toString()), entity.getProperty(
						"subject").toString(), entity.getProperty("body")
						.toString(), Long.parseLong(entity.getProperty(
						"group_id").toString()));
				messages.add(message);
			}
		}
		return messages;
	}

	public boolean saveMessage() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("messagas");
		PreparedQuery pq = datastore.prepare(gaeQuery);

		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());
		Entity messagas = new Entity("messagas", list.size() + 2);
		try {

			messagas.setProperty("subject", this.subject);
			messagas.setProperty("body", this.body);
			messagas.setProperty("from", this.from);
			messagas.setProperty("to", to);
			messagas.setProperty("group_id", this.group_id);
			datastore.put(messagas);
			txn.commit();

		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		return true;
	}
}
