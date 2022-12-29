package com.project.EmployeeStressMeasure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	// 요청 - 뷰 연결
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("employees/list");
		registry.addViewController("/login").setViewName("member/login");	// 경로 잡아야함 
		registry.addViewController("/measure").setViewName("measure/list");
		registry.addViewController("/signup").setViewName("member/signup");
	}
}
