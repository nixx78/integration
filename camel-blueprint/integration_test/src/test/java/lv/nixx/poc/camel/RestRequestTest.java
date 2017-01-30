package lv.nixx.poc.camel;

import org.junit.Test;

public class RestRequestTest {
	
	final String url = "http://localhost:8181/cxf/blueprint_sample_app/hello/1234";

	@Test
	public void testOnce() throws Exception {
		HttpRequest req = HttpRequest.create();
		String result = req.executeGetRequest(url);

		System.out.println(result);
	}
	
	@Test
	public void testInPeriod() throws InterruptedException {
		
		
		Thread t = new Thread(() -> {
			final HttpRequest req = HttpRequest.create();
			int i = 0;
			while(true) {
				try {
					String r = req.executeGetRequest(url);
					System.out.println(i+ ":" + r);
					
					synchronized (req) {
						req.wait(100 * 10);
					}
					
					i++;
				} catch (Exception e) {
					System.err.println(e);
				}
			}	
		}, "Server call thread");
		
		t.start();
		t.join();
	}

}
