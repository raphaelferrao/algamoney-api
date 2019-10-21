package com.example.algamoney.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;

public class GenericController {
	
	@Autowired
	protected ApplicationEventPublisher publisher; 
	
	@Autowired
	protected MessageSource messageSource;

}
