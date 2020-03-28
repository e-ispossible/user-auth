package com.salle.user.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return 
			new Docket(DocumentationType.SWAGGER_2).select()
			.apis(RequestHandlerSelectors.basePackage("com.salle.user.controller"))
			.paths(PathSelectors.ant("/user/api/**"))
			.build().apiInfo(apiEndPoints())
			.globalOperationParameters(
				Arrays.asList(
					new ParameterBuilder()
					.allowEmptyValue(true)
					.name("X-Auth-Token")
					.parameterType("header")
					.modelRef(new ModelRef("string"))
					.required(true)
					.description("Prada™ Api request requires a valid authenticated token")
					.build()
				)
			);
			
	}
	private ApiInfo apiEndPoints() {
		return 
			new ApiInfoBuilder().title("Prada REST API")
			.description("Reservation Management")
			.contact(new Contact("Prada Dev. team", "www.dasanm.co.kr","david@dasanm.co.kr"))
			//.license("LGPL 1.0")
			.version("0.1")
			.build();
	}
}