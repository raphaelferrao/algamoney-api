package com.example.algamoney.api.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class StandardErrorDTO implements Serializable {
	
	private static final long serialVersionUID = 6195525444867818110L;

	private Long timestamp;
	
	private Integer status;
	
	private String error;
	
	@JsonInclude(Include.NON_NULL)
	private List<ErrorDTO> messages;
	
	private String path;
	
	public StandardErrorDTO() {
		
	}
	
	public StandardErrorDTO(Long timestamp, Integer status, String error, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.path = path;
	}

	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public List<ErrorDTO> getMessages() {
		if (messages == null) {
			messages = new ArrayList<>();
		}
		return messages;
	}
	public void setMessages(List<ErrorDTO> messages) {
		this.messages = messages;
	}
	
}
