package com.example.algamoney.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public class GenericController {
	
	@Autowired
	protected ApplicationEventPublisher publisher; 

}
