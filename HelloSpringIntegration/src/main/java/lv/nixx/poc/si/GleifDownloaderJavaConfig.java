package lv.nixx.poc.si;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.endpoint.MethodInvokingMessageSource;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.support.FileExistsMode;

@SpringBootApplication
@Configuration
@Profile(GleifDownloaderJavaConfig.currentProfile)
public class GleifDownloaderJavaConfig {
	
	public static final String currentProfile = "Java_Config";

	public static void main(String[] args) throws Exception {
		SpringApplication springApplication = new SpringApplication(GleifDownloaderJavaConfig.class);
		springApplication.setAdditionalProfiles(currentProfile);

		ConfigurableApplicationContext ctx = springApplication.run(args);
		System.out.println("Hit Enter to terminate");
		System.in.read();
		ctx.close();
	}
	
	@Bean
	public MethodInvokingMessageSource gleifDownloadStartMessage() {
		MethodInvokingMessageSource source = new MethodInvokingMessageSource();
        source.setObject(this);
        source.setMethodName("getGleifDwonloadMessage");
        return source;
	}
	
	public Message<String> getGleifDwonloadMessage() {
		Map<String, Object> h = new HashMap<>();
		final String date = "20160919";
		h.put("date", date);
		h.put("directory", "/tmp/gleif/out");
		h.put(FileHeaders.FILENAME, date + "-GLEIF-concatenated-file.zip");
		
	    return new GenericMessage<>(System.currentTimeMillis() + "", new MessageHeaders(h));
	}


    @Bean
    public MessageHandler httpGateway() throws URISyntaxException {
        SpelExpressionParser expressionParser = new SpelExpressionParser();
        final String expressionString = "'https://www.gleif.org/lei-files/' + headers['date'] + '/GLEIF/' + headers['" + FileHeaders.FILENAME + "']";
		HttpRequestExecutingMessageHandler httpHandler = new HttpRequestExecutingMessageHandler(expressionParser.parseExpression(expressionString));
	    httpHandler.setExpectedResponseType(byte[].class);
	    httpHandler.setHttpMethod(HttpMethod.GET);
        return httpHandler;
    }
	
	@Bean
	public IntegrationFlow gleifDownloadFlow(MessageHandler httpGateway) throws URISyntaxException {
		return IntegrationFlows.from(gleifDownloadStartMessage(), c -> c.poller(Pollers.fixedRate(1000).maxMessagesPerPoll(1)))
				.handle("myService", "process")
				.handle(httpGateway)
  	            .handleWithAdapter(a -> a.file(m -> m.getHeaders().get("directory")).fileExistsMode(FileExistsMode.IGNORE))
				.get();
	}
	
	@Bean
	public MyService myService() {
		return new MyService();
	}
	
	class MyService {
		public Message<?> process(Message<?> msg) {
			System.out.println("process" + msg);
			return msg;
		}
	}
		
	

}
