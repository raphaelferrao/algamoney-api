package com.example.algamoney.api.repository.filter;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class LancamentoFilter implements Serializable {

	private static final long serialVersionUID = 8252480665264402296L;
	
	private String descricao;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVencimentoDe;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVencimentoPara;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public LocalDate getDataVencimentoDe() {
		return dataVencimentoDe;
	}
	public void setDataVencimentoDe(LocalDate dataVencimentoDe) {
		this.dataVencimentoDe = dataVencimentoDe;
	}
	
	public LocalDate getDataVencimentoPara() {
		return dataVencimentoPara;
	}
	public void setDataVencimentoPara(LocalDate dataVencimentoPara) {
		this.dataVencimentoPara = dataVencimentoPara;
	}
	
}
