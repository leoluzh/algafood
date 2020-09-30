package com.lambdasys.food;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class NoWebApplication {

	public static void main( String[] args ) {
		ApplicationContext context = new SpringApplicationBuilder(NoWebApplication.class)
			.web(WebApplicationType.NONE).run(args);
	}
	
}
