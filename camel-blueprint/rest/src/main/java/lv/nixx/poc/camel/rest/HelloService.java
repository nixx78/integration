package lv.nixx.poc.camel.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lv.nixx.poc.camel.persistence.PersonDAO;
import lv.nixx.poc.camel.util.MessageUtil;
import lv.nixx.poc.camel.util.MessageUtilInstance;

public class HelloService  {
	
	private PersonDAO dao;
	private MessageUtilInstance util;
	private Logger log = LoggerFactory.getLogger(HelloService.class);
	
	public HelloResponse processHelloMessage(String name) {
		// Sample, how we can control states and so on.
		// return Response.ok(new HelloResponse("Hello " + name)).status(Status.OK).build();

		log.info("HelloService is called with 'name':" + name);
		
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
