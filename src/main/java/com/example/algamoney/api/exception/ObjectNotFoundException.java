package com.example.algamoney.api.exception;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5900126785839207021L;
	
	public ObjectNotFoundException(String message) {
		super(message);
	}
	
}
