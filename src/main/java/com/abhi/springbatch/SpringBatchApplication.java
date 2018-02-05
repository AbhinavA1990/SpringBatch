package com.abhi.springbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/***
 * Not meant to be deployed as a war "Standalone app" For it to be deployed as a
 * war remove the web env flag run as maven build with Goal:clean install
 * spring-boot:run
 * 
 * @author Abhinav
 *
 */
@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
public class SpringBatchApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SpringBatchApplication.class);
		app.setWebEnvironment(false);
		app.run(args);
	}
}
