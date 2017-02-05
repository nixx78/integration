## Project description 

This sample project shows how to build application from 
different bundles. Each bundle located in separate project, to "clue" bundles 
is used technologies OSGI and Blueprint.

## Sample running

1. Download: [Apache Karaf](https://karaf.apache.org/download.html)
1. Setup Karaf using [standard instructions](https://karaf.apache.org/manual/latest/#_quick_start)
1. Build projects, in directory: _camel-blueprint_
 
		mvn clean install 

Run Karaf and execute the following commands in Karaf console:
	
	feature:repo-add mvn:lv.nixx.poc.camel/feature/0.0.1-SNAPSHOT/xml/features
	feature:install nixx-camel-blueprint-example
	
After: list command execution output must be like this:

	ID	 | State  | Lvl | Version        | Name
	--------------------------------------------------------------------------------------------------
	1652 | Active |  80 | 1.9.13         | Jackson JSON processor
	1653 | Active |  80 | 1.9.13         | JAX-RS provider for JSON content type, using Jackson data binding
	1654 | Active |  80 | 1.9.13         | Data mapper for Jackson JSON processor
	1656 | Active |  80 | 0.0.1.SNAPSHOT | NiXX: Blueprint: PoC: model
	1658 | Active |  50 | 2.17.0         | camel-blueprint
	1659 | Active |  50 | 2.17.0         | camel-catalog
	1660 | Active |  80 | 2.17.0         | camel-commands-core
	1661 | Active |  50 | 2.17.0         | camel-core
	1662 | Active |  50 | 2.17.0         | camel-cxf
	1663 | Active |  50 | 2.17.0         | camel-cxf-transport
	1664 | Active |  50 | 2.17.0         | camel-jaxb
	1665 | Active |  50 | 2.17.0         | camel-spring
	1666 | Active |  80 | 2.17.0         | camel-karaf-commands
	1691 | Active |  80 | 1.1.1          | geronimo-jms_1.1_spec
	1692 | Active |  80 | 2.0.9          | Apache MINA Core
	1707 | Active |  80 | 0.0.1.SNAPSHOT | NiXX: Blueprint: PoC: persistence
	1708 | Active |  80 | 0.0.1.SNAPSHOT | NiXX: Blueprint: PoC: rest
	1709 | Active |  80 | 0.0.1.SNAPSHOT | NiXX: Blueprint: PoC: util	
	
	
Open browser and type: http://localhost:8181/cxf/blueprint\_sample\_app/hello/John

Output should be something like:

	{"message":"Hello my dear friend #JOHN#"}
	
## Integration test
  Integration test (server call using REST client) is located in class: _RestRequestTest_
  
 
