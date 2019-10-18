package com.example.algamoney.api.exception;

import java.io.Serializable;

public class ErrorDTO implements Serializable {

	private static final long serialVersionUID = -1500553056341825933L;
	
	private String message;
	private String friendlyMessage;
	
	public ErrorDTO(String message, String friendlyMessage) {
		super();
		this.message = message;
		this.friendlyMessage = friendlyMessage;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getFriendlyMessage() {
		return friendlyMessage;
	}
	public void setFriendlyMessage(String friendlyMessage) {
		this.friendlyMessage = friendlyMessage;
	}
	
	
	
}
