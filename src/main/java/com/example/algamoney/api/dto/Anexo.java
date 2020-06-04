package com.example.algamoney.api.dto;

import java.io.Serializable;

public class Anexo implements Serializable {

	private static final long serialVersionUID = -4432296248964574382L;

	private String nome;
	
	private String url;

	public Anexo(String nome, String url) {
		super();
		this.nome = nome;
		this.url = url;
	}

	public String getNome() {
		return nome;
	}

	public String getUrl() {
		return url;
	}
	
}
