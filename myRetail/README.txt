1.Please see pom.xml for Java, Spring, Spring Boot versions.
2.Application is built on Spring Boot as a RESTful service with GET and PUT actions for Product portfolio
3.Nitrite NoSql file based database is used for storing Product price details (tried for Hbase but could not make it running on windows using Cygwin)
4.Application has 2 profiles for dev & prod environments
5.SLF4J is used with Log4j as implementer
6.There is Controller, Service and DAO layers for handling the use case requirements

Process to run:

1. Executable jar: myRetail.jar can be run with java -jar command. Application runs on 8080. It can be changed either in application.properties or through JVM argument:-Dserver.port
2. Application creates nitrite db file path @ C:\Arun\software\nitrite\myretail.db . It can be changed in application.properties file
3. Input request for GET: http://localhost:8080/products/{id} and output would be:
	{
	    "id": "16696652",
	    "name": "Beats Solo 2 Wireless - Black (MHNG2AM/A)",
	    "current_price": {
	        "value": "12.60",
	        "currency_code": "USD"
	    },
	    "service_response": "Success"
	}
4. Input request for PUT: http://localhost:8080/products/{id}
	{
	    "id": "16696652",
	    "name": "Beats Solo 2 Wireless - Black (MHNG2AM/A)",
	    "current_price": {
	        "value": "12.60",
	        "currency_code": "USD"
	    },
	    "service_response": "Success"
	}
5. JUnit test report will be created in target/surefire-reports folder as surefire plugin is used for the same (JUnit is written for only Controller considering limited time for usecase)
6. Log file gets created @ C:\Arun\software\git\java-development\myRetail\dev . This can be changed in log4j.properties