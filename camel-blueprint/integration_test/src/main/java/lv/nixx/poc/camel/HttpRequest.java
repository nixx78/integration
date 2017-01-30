package lv.nixx.poc.camel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpRequest {
	
	private HttpClient client;
	
	private HttpRequest() {
	}
	
	public static HttpRequest create() {
		HttpRequest r = new HttpRequest();
		r.client = HttpClientBuilder.create().build();
		return r;
	}
	
	public String executeGetRequest(String url) throws Exception {
		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}
	

}
