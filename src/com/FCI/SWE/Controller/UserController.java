package com.FCI.SWE.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.User;
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
public class UserController {
	@GET
	@Path("/newmessage")
	public Response newMessage() {
		return Response.ok(new Viewable("/jsp/newmessage")).build();
	}
	@Path("/addNewMessage")
	@Produces(MediaType.TEXT_PLAIN)
	public String sendrequest(@FormParam("subject") String subject,@FormParam("body") String body,@FormParam("to") String to) {
		String serviceUrl = "addNewMessageService";
	 
			UserEntity userto = UserEntity.getUser(to);
			
			if(userto== null)
				return "Failed <br /> User not found";

 			String urlParameters = "subject=" + subject + 
 								   "&body="+body +
 								   "&to="+userto.getId()+
								   "&from=" + User.getCurrentActiveUser().getId();
		 
			String line, retJson = Connection.connect(serviceUrl, urlParameters, "POST", "application/x-www-form-urlencoded;charset=UTF-8");
						JSONParser parser = new JSONParser();
			Object obj;
			try {
				obj = parser.parse(retJson);
				JSONObject object = (JSONObject) obj;
				if (object.get("Status").equals("OK")){
					userto.sendNotify();
					return "Message sent Successfully";
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		 
		return "Failed";
	}
	/**
	 * Action function to render Signup page, this function will be executed
	 * using url like this /rest/signup
	 * 
	 * @return sign up page
	 */
	@POST
	@Path("/doSearch")
	public Response usersList(@FormParam("uname") String uname){
		System.out.println(uname);
		String serviceUrl = "http://localhost/rest/SearchService";
		String urlParameters = "uname=" + uname;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		return null;
	}
	@GET
	@Path("/signup")
	public Response signUp() {
		return Response.ok(new Viewable("/jsp/register")).build();
	}

	
	@GET
	@Path("/search")
	public Response search(){
		return Response.ok(new Viewable("/jsp/search")).build();
	}
	/**
	 * Action function to render home page of application, home page contains
	 * only signup and login buttons
	 * 
	 * @return enty point page (Home page of this application)
	 */
	@GET
	@Path("/")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}

	/**
	 * Action function to render login page this function will be executed using
	 * url like this /rest/login
	 * 
	 * @return login page
	 */
	@GET
	@Path("/login")
	public Response login() {
		return Response.ok(new Viewable("/jsp/login")).build();
	}

	/**
	 * 
	 * 
	 * @return send freind request page
	 */
	@GET
	@Path("/addFreind")
	public Response addFreind() {
		System.out.println("Current:"+ User.getCurrentActiveUser().getEmail());
		return Response.ok(new Viewable("/jsp/addFreind")).build();
	}

	/**
	 * 
	 * 
	 * @return send freind request page
	 */
	@GET
	@Path("/sendFreiendRequest")
	public Response sendFreiendRequest() {
		System.out.println("Current:"+ User.getCurrentActiveUser().getEmail());
		return Response.ok(new Viewable("/jsp/request")).build();
	}

	/**
	 * Action function to render logout page this function will be executed
	 * using url like this /rest/logout
	 * 
	 * @return logout page
	 */
	@GET
	@Path("/logout")
	public Response logout() {
		
		User.logout();
		return Response.ok(new Viewable("/jsp/login")).build();
	}

	/**
	 * Action function to response to signup request, This function will act as
	 * a controller part and it will calls RegistrationService to make
	 * registration
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided user password
	 * @return Status string
	 */
	@POST
	@Path("/response")
	@Produces(MediaType.TEXT_PLAIN)
	public String response(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		String serviceUrl = "RegistrationService";		
		String urlParameters = "uname=" + uname + "&email=" + email
				+ "&password=" + pass;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			// System.out.println(retJson);
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "Registered Successfully";

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return "Failed";
	}

	@POST
	@Path("/sendrequest")
	@Produces(MediaType.TEXT_PLAIN)
	public String sendrequest(@FormParam("user1") String user1) {
		String serviceUrl = "sendFriendRequestService";
	 
 			String urlParameters = "user1=" + user1 + 
								   "&user2=" + User.getCurrentActiveUser().getEmail();
		 
			String line, retJson = Connection.connect(serviceUrl, urlParameters, "POST", "application/x-www-form-urlencoded;charset=UTF-8");
						JSONParser parser = new JSONParser();
			Object obj;
			try {
				obj = parser.parse(retJson);
				JSONObject object = (JSONObject) obj;
				if (object.get("Status").equals("OK"))
					return "Request sent Successfully";

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		return "Failed";
	}
	@POST
	@Path("/addFriend")
	@Produces(MediaType.TEXT_PLAIN)
	public String saddFriend(@FormParam("user2") String user2) {
		String serviceUrl = "addFriendService";
		try {
 			String urlParameters = "user1=" + User.getCurrentActiveUser().getEmail() +"&user2=" + user2 ;
			
			System.out.println("Current:"+ User.getCurrentActiveUser().getEmail());
		 
			String line, retJson = Connection.connect(serviceUrl, urlParameters, "POST", "application/x-www-form-urlencoded;charset=UTF-8");
			 
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "Added Successfully";
		 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		return "Failed";
	}

	/**
	 * Action function to response to login request. This function will act as a
	 * controller part, it will calls login service to check user data and get
	 * user from datastore
	 * 
	 * @param uname
	 *            provided user name
	 * @param pass
	 *            provided user password
	 * @return Home page view
	 */
	@POST
	@Path("/home")
	@Produces("text/html")
	public Response home(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		String urlParameters = "uname=" + uname + "&password=" + pass;

		String retJson = Connection.connect(
				"LoginService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			User user = User.getUser(object.toJSONString());
			map.put("name", user.getName());
			map.put("email", user.getEmail());
			return Response.ok(new Viewable("/jsp/home", map)).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return null;

	}

}