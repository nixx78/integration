package lv.nixx.poc.camel.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// http://camel.apache.org/cxfrs.html
public class HelloRestService {
	
	@GET
	@Path(value = "/hello/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public HelloResponse sayHello(@PathParam(value = "name") String name) {
		return null;
	}

}
