package lv.nixx.poc.camel.files.jaxb;

import lv.nixx.poc.camel.common.CamelRunner;
import lv.nixx.poc.camel.model.Response;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

public class FilesProcessingWithJAXBSample {
	
	public static void main(String args[]) throws Exception {
		CamelRunner runner = new CamelRunner();
		runner.main().bind("model", new JaxbDataFormat("lv.nixx.poc.camel.model"));
		runner.main().bind("personProcessor", new PersonProcessor());
		runner.main().bind("responseCreator", new ResponseCreator());
		runner.main().bind("errorHandler", new ErrorHandler());

		runner.main().addRouteBuilder(new FileFlowRouteBuilder());
		runner.run();
	}
	
}

class FileFlowRouteBuilder extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		errorHandler(deadLetterChannel("bean:errorHandler"));
		
		from("file:data/inbox?move=../done/")
		.unmarshal("model")
		.setHeader("personCount", simple("${body.size}"))
		.log("Total person count [${header.personCount}] in file [${file:name}]")
		.split(simple("${body.persons}"))
		.parallelProcessing()
		.process("personProcessor")
		.aggregate(header("id"), new ResponseAggregatorStrategy())
		.completionSize(header("personCount"))
		.completionTimeout(1000)
		.log("Success [${header.success}] fail [${header.fail}]")
		.split(body())
		.choice()
			.when( bodyAs(Response.class).method("isSuccess").isEqualTo(true)).to("direct:success").endChoice()
			.when( bodyAs(Response.class).method("isSuccess").isEqualTo(false)).to("direct:fail").endChoice();
		
		from("direct:success")
		.aggregate(header("id"), new ResponseAggregatorStrategy())
		.completionSize(header("success"))
		.eagerCheckCompletion()
		.completionTimeout(100000)
		.process("responseCreator")
		.to("file:data/ok_outbox")
		.end();

		from("direct:fail")
		.aggregate(header("id"), new ResponseAggregatorStrategy())
		.completionSize(header("fail"))
		.eagerCheckCompletion()
		.completionTimeout(10000)
		.process("responseCreator")
		.to("file:data/fail_outbox")
		.end();
	}
	
}

