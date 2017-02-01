package lv.nixx.poc.camel.rest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;

public class RestRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		addRestRoute();
		addSayHelloRoute();
	}

	private void addRestRoute() {
		from("cxfrs:bean:helloServiceEndpoint?bindingStyle=SimpleConsumer&provider=#jsonProvider")
		.log("REST method called: ${in.header." + CxfConstants.OPERATION_NAME + "}")
		.routeId("restRouting")
		.choice()
			.when(header(CxfConstants.OPERATION_NAME).isEqualTo("sayHello")).to("direct:sayHello").endChoice()
		.end();
	}
	
	private void addSayHelloRoute() {
		from("direct:sayHello")
		.routeId("sayHelloRoute")
		.bean("helloService", "processHelloMessage")
		.removeHeader("Content-Length")
		.end();
	}


}
