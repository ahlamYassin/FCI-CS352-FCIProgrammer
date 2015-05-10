package com.FCI.SWE.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;

public class NotificationController {
	@GET
	@Path("/notifications")
	public Response notifications() {
		return Response.ok(new Viewable("/jsp/notifications")).build();
	}

}
