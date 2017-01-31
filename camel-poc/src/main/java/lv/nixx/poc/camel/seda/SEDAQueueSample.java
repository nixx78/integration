package lv.nixx.poc.camel.seda;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import lv.nixx.poc.camel.common.CamelRunner;

public class SEDAQueueSample {

	public static void main(String args[]) throws Exception {
		
		CamelRunner runner = new CamelRunner();
		runner.main().addRouteBuilder(new RouteBuilder() {
			public void configure() {
	            from("timer:simpleTimer?period=5000").to("seda:queue?multipleConsumers=true").end();
	            
	            from("seda:queue?multipleConsumers=true")
		            .routeId("firstRoute")
		            .process(new Processor() {
						@Override
						
						public void process(Exchange exchange) throws Exception {
			            	System.out.println("--- BeanOne is called");
						}
		            })
		            .end();

	            from("seda:queue?multipleConsumers=true")
	            .routeId("secondRoute")
	            .process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
		            	System.out.println("+++ BeanTwo is called");
					}
	            })
	            .end();
	            
			}
		});
		
		runner.run();
	}


}
