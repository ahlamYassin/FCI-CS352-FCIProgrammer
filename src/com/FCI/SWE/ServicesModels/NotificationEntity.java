package com.FCI.SWE.ServicesModels;

import java.sql.Timestamp;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class NotificationEntity {

	private long user;
	private String message;
	private String time;
	private long id;
	/**
	 * @param user
	 * @param message
	 * @param time
	 */
	public NotificationEntity(long user, String message, String time) {
		super();
		this.user = user;
		this.message = message;
		this.time = time;
	}
	public long getUser() {
		return user;
	}

	public void setUser(long user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
 
	public Boolean saveNotification() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());

		try {
			Entity notification = new Entity("notifications", list.size() + 2);

			notification.setProperty("user", this.user);
			notification.setProperty("message", this.message);
			notification.setProperty("time", this.time.toString());

			datastore.put(notification);
			txn.commit();

		}catch(Exception e){
			e.printStackTrace();
		}
		finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		return true;
	}
}
