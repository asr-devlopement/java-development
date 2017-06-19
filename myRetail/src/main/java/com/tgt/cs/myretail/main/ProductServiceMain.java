package com.tgt.cs.myretail.main;

import org.dizitart.no2.Nitrite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import org.springframework.beans.factory.DisposableBean;

@SpringBootApplication(scanBasePackages={"com.tgt.cs.myretail.main"}, exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class ProductServiceMain implements DisposableBean {
	
	public static final Logger logger = LoggerFactory.getLogger(ProductServiceMain.class);
	
	@Autowired
	Nitrite nitrite;
	
	public static void main(String[] args) {
        SpringApplication.run(ProductServiceMain.class, args);
    }
	
	@Override
	public void destroy() throws Exception {

		logger.info("MyRetail Spring Boot --> Destroying DB connections......");
		nitrite.close();

	} 
}
