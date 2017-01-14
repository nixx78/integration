package lv.nixx.poc.camel.rest;

public class HelloService  {
	
	public HelloResponse processHelloMessage(String name) {
		// Sample, how we can control states and so on.
		// return Response.ok(new HelloResponse("Hello " + name)).status(Status.OK).build();
		return new HelloResponse("Hello " + name);
	}

}
