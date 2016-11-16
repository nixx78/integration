package lv.nixx.poc.si;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("integration.xml")
public class GleifDownloaderXMLConfig {

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext ctx = new SpringApplication(GleifDownloaderXMLConfig.class).run(args);
		System.out.println("Hit Enter to terminate");
		System.in.read();
		ctx.close();
	}

}
