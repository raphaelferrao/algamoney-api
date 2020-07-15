package com.example.algamoney.api.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class Endereco implements Serializable {
	
	private static final long serialVersionUID = -7728643366167652453L;

	@NotNull
	@Size(min=9, max=9)
	private String cep;
	
	@NotNull
	@Size(min=10, max=100)
	private String logradouro;
	
	@Size(max=20)
	private String numero;
	
	@Size(max=50)
	private String complemento;
	
	@NotNull
	@Size(min=5, max=50)
	private String bairro;
	
	@ManyToOne
	@JoinColumn(name = "codigo_cidade")
	private Cidade cidade;

	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

}
