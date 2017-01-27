package lv.nixx.poc.camel.rest;

import lv.nixx.poc.camel.persistence.PersonDAO;
import lv.nixx.poc.camel.util.MessageUtil;
import lv.nixx.poc.camel.util.MessageUtilInstance;

public class HelloService  {
	
	private PersonDAO dao;
	private MessageUtilInstance util;
	
	public HelloResponse processHelloMessage(String name) {
		// Sample, how we can control states and so on.
		// return Response.ok(new HelloResponse("Hello " + name)).status(Status.OK).build();

		name = util.convert(name);
		return new HelloResponse(dao.getGreeting() + " " + MessageUtil.convertToUppercase(name));
	}

	public void setDao(PersonDAO dao) {
		this.dao = dao;
	}

	public void setUtil(MessageUtilInstance util) {
		this.util = util;
	}

}
