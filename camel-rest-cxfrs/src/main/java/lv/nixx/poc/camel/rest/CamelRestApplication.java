package lv.nixx.poc.camel.rest;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.Main;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.javaconfig.CamelConfiguration;

public class CamelRestApplication extends CamelConfiguration {
	
	public static void main(String[] args) throws Exception {
		new Main().run();
	}
	
    @Override
    protected CamelContext createCamelContext() throws Exception {
        return new SpringCamelContext(getApplicationContext());
    }

}
