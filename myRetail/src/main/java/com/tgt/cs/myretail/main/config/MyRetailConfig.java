package com.tgt.cs.myretail.main.config;

import org.dizitart.no2.Nitrite;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.client.RestTemplate;




@Configuration
@PropertySource("classpath:application.properties")
public class MyRetailConfig {


	@Value("${nitrite.db.path}")
	private String nitriteDbPath;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	
	@Bean
	public Nitrite getNitrite() {
			
		Nitrite db = Nitrite.builder()
		        .compressed()
		        .filePath(nitriteDbPath)
		        .openOrCreate("user", "password");
		
		return db;
	}
}
