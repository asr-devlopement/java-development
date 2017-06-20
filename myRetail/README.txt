1.Please see pom.xml for Java, Spring, Spring Boot versions.
2.Application is built on Spring Boot as a RESTful service with GET and PUT actions for Product portfolio
3.Nitrite NoSql file based database is used for storing Product price details (tried for Hbase but could not make it running on windows using Cygwin)
4.Application has 2 profiles for dev & prod environments
5.SLF4J is used with Log4j as implementer
6.There is Controller, Service and DAO layers for handling the use case requirements

Process to run:

1. Executable jar: myRetail.jar can be run with java -jar command. Application runs on 8080. It can be changed either in application.properties or through JVM argument:-Dserver.port
2. Application creates nitrite db file path @ C:\Arun\software\nitrite\myretail.db . Already created instance is present project's 'db' folder which can be placed in the same folder. Path of file can be changed in application.properties file
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
7. target folder have the jar file and junit test reports.
8. Run command: mvn install , for packaging the application to jar and mvn test , just for running the junits
9. Chrome Postman or ARC can be used as rest client to test the application
10. Nitrite DB file is placed in 'db' folder inside project

