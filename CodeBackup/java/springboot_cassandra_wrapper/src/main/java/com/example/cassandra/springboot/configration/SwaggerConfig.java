package com.example.cassandra.springboot.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * api()
	 * @return
	 */
	@Bean
	public Docket api() {
	    return new Docket(DocumentationType.SWAGGER_2)
	      .apiInfo(apiInfo())
	      .select()
	      .apis(RequestHandlerSelectors.basePackage("com.example.cassandra.springboot.controller"))
	      .paths(PathSelectors.any())
	      .build();
	    
	}

	/**
	 * apiInfo()
	 * @return
	 */
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "OverC360",
	      "OverC360 Service Specifications",
	      "1.0",
	      "Terms of service",
	      new Contact("Classic WMS Team", "www.tekclover.com", "overc360@tekclover.com"),
	      "License of API",
	      "API license URL",
	      Collections.emptyList());
	}
}
