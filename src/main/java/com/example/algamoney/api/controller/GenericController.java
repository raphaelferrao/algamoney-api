package com.example.algamoney.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;

import com.example.algamoney.api.config.ApiProperty;

public class GenericController {
	
	@Autowired
	protected ApplicationEventPublisher publisher; 
	
	@Autowired
	protected MessageSource messageSource;
	
	@Autowired
	protected ApiProperty apiProperty; 

}
