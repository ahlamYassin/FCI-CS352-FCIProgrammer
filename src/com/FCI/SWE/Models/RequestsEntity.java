package com.FCI.SWE.Models;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.ServicesModels.UserEntity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * <h1>User Entity class</h1>
 * <p>
 * This class will act as a model for user, it will holds user data
 * </p>
 *
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 */
public class RequestsEntity {
	private String user1;
	private String user2;
	private String status;

	/**
	 * Constructor accepts user data
	 * 
	 * @param user1
	 *            user name
	 * @param user2
	 *            user email
	 * @param status
	 *            user provided password
	 */
	public RequestsEntity(String user1, String user2, String status) {
		this.user1 = user1;
		this.user2 = user2;
		this.status = status;

	}

	/**
	 * Constructor accepts user data
	 * 
	 * @param user1
	 *            user1 email
	 * @param user2
	 *            user2 email
	 * 
	 */
	public RequestsEntity(String user1, String user2) {
		this(user1, user2, "0");

	}
	
	public String getUser1() {
		return user1;
	}


	public void setUser1(String user1) {
		this.user1 = user1;
	}


	public String getUser2() {
		return user2;
	}


	public void setUser2(String user2) {
		this.user2 = user2;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	/* 
	 * This static method will form RequestsEntity class using json format
	 * contains user data
	 * 
	 * @param json
	 *            String in json format contains user data
	 * @return Constructed user entity
	 */
	public static RequestsEntity getRequests(String json) {

		JSONParser parser = new JSONParser();
		try {
			JSONObject object = (JSONObject) parser.parse(json);
			return new RequestsEntity(object.get("user1").toString(), object
					.get("user2").toString(), object.get("status").toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * This static method will form RequestsEntity class using user name and
	 * password This method will serach for requests in datastore
	 * 
	 * @param user1
	 *             
	 * @param status
	 *            
	 * @return Constructed requests entity
	 */

	public static RequestsEntity getRequests(String user1, String user2, String status) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("requests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			System.out.println(entity.getProperty("user1").toString()+"  "+entity.getProperty("user2").toString()+"  "+entity.getProperty("status").toString());
			if (entity.getProperty("user1").toString().equals(user1) &&
					entity.getProperty("user2").toString().equals(user2)
					&& entity.getProperty("status").toString().equals(status)) {
				RequestsEntity returnedUser = new RequestsEntity(entity
						.getProperty("user1").toString(), entity.getProperty(
						"user2").toString(), entity.getProperty("status")
						.toString());
				return returnedUser;
			}
		}

		return null;
	}

	/**
	 * This method will be used to save user object in datastore
	 * 
	 * @return boolean if user is saved correctly or not
	 */
	public Boolean saveRequest() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("requests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		if(UserEntity.getUser(user1) != null && UserEntity.getUser(user2) != null){
			Entity request = new Entity("requests", list.size() + 1);
	
			request.setProperty("user1", this.user1);
			request.setProperty("user2", this.user2);
			request.setProperty("status", this.status);
			datastore.put(request);
	
			return true;
		}
		return false;
		

	}
}
