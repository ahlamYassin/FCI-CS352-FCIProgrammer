package com.FCI.SWE.Services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONObject;

import com.FCI.SWE.Models.RequestsEntity;
import com.FCI.SWE.ServicesModels.MessageEntity;
import com.FCI.SWE.ServicesModels.UserEntity;

/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces("text/html")
public class Service {
	
	
	/*@GET
	@Path("/index")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}*/


		/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass);
		user.saveUser();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}

	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname, pass);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
			object.put("id", user.getId());
		}

		return object.toString();

	}

	 
	@POST
	@Path("/sendFriendRequestService")
	public String sendFriendRequestService(@FormParam("user1") String user1,
			@FormParam("user2") String user2) {
		JSONObject object = new JSONObject();
		RequestsEntity myRequest = new RequestsEntity(user1, user2);
		if (!myRequest.saveRequest()) {
			object.put("Status", "Failed");
		} else {
			object.put("Status", "OK");
			object.put("user1", myRequest.getUser1());
			object.put("user2", myRequest.getUser2());
		}

		return object.toString();

	}
	@POST
	@Path("/addFriendService")
	public String addFriendService(@FormParam("user1") String user1,
			@FormParam("user2") String user2) {
		JSONObject object = new JSONObject();
		RequestsEntity myRequest = RequestsEntity.getRequests(user1, user2, "0");
		if (myRequest == null) {
			object.put("Status", "Failed");
		} else {
			myRequest.setStatus("1");
			myRequest.saveRequest();
			object.put("Status", "OK");
			object.put("user1", myRequest.getUser1());
			object.put("user2", myRequest.getUser2());
		}

		return object.toString();

	}
	
	@POST
	@Path("/addNewMessageService")
	public String addNewMessageService(@FormParam("subject") String subject,
			@FormParam("body") String body,@FormParam("to") long to,@FormParam("from") long from ) {
		JSONObject object = new JSONObject();
		MessageEntity myRequest = new MessageEntity(from, subject, body, to);
		if (!myRequest.saveMessage()) {
			object.put("Status", "Failed");
		} else {
			object.put("Status", "OK");
		 
		}

		return object.toString();

	}
}