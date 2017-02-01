package lv.nixx.poc.camel.rest;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import lv.nixx.poc.camel.common.CamelRunner;

/*
 * Jetty + SEDA
 * 
 */

public class ReastAndSedaSample {

	public static void main(String[] args) throws Exception {

		CamelRunner cr = new CamelRunner();
		cr.main().addRouteBuilder(new RouteBuilder() {

			public void configure() {
				
				from("jetty:http://localhost:8181/mytestservice/")
					.routeId("restRouting")
					.to("seda:processMessage?multipleConsumers=true")
					.process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							System.out.println(Thread.currentThread().getName() +  ":Hello Mr");
							exchange.getOut().setBody("Hello world Mr ");
						}
					}).end();
				
				
				from("seda:processMessage?multipleConsumers=true")
					.routeId("logRoute")
					.process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							System.out.println(Thread.currentThread().getName() +  ":Log message");
						}
					}).end();

				from("seda:processMessage?multipleConsumers=true")
				.routeId("auditRoute")
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						System.out.println(Thread.currentThread().getName() +  ":Audit message");
					}
				}).end();	
				
			}	

				
		});
		cr.run();
	}

}
