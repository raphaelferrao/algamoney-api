package com.example.algamoney.api.exception;

public class PessoaInexistenteOuInativaException extends RuntimeException {

	private static final long serialVersionUID = -158660016130512698L;

	public PessoaInexistenteOuInativaException() {
		super();
	}
	
	public PessoaInexistenteOuInativaException(String message) {
		super(message);
	}
}
