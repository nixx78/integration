package lv.nixx.poc.camel.rest;

public class HelloResponse {
	
	private String message;

	public HelloResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}
