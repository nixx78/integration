package lv.nixx.poc.camel.rest;

import lv.nixx.poc.camel.persistence.PersonDAO;

public class HelloService  {
	
	private PersonDAO dao;
	
	public HelloResponse processHelloMessage(String name) {
		// Sample, how we can control states and so on.
		// return Response.ok(new HelloResponse("Hello " + name)).status(Status.OK).build();

		return new HelloResponse(dao.getGreeting() + " " + name);
	}

	public void setDao(PersonDAO dao) {
		this.dao = dao;
	}

}
