package com.fei;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.fei.springboot"})
public class Application{

	protected static Logger logger= LoggerFactory.getLogger(Application.class);

	 public static void main(String[] args) throws Exception {
		 ApplicationContext context = new SpringApplicationBuilder(Application.class)
				 .properties("server.port=8081")
				 .run(args);
		 logger.info("SpringBoot Start Success");
	 }

}
