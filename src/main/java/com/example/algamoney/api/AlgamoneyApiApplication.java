package com.example.algamoney.api;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.context.ApplicationContext;

import com.example.algamoney.api.config.ApiProperty;

@SpringBootApplication(exclude = ContextStackAutoConfiguration.class)
@EnableConfigurationProperties(ApiProperty.class)
public class AlgamoneyApiApplication {
	
	private static ApplicationContext APPLICATION_CONTEXT;

	public static void main(String[] args) {
		APPLICATION_CONTEXT = SpringApplication.run(AlgamoneyApiApplication.class, args);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	
	public static <T> T getBean(Class<T> type) {
		return APPLICATION_CONTEXT.getBean(type);
	}
}
