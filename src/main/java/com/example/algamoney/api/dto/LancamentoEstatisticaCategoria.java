package com.example.algamoney.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.example.algamoney.api.model.Categoria;

public class LancamentoEstatisticaCategoria implements Serializable {
	
	private Categoria categoria;
	
	private BigDecimal total;

	public LancamentoEstatisticaCategoria(Categoria categoria, BigDecimal total) {
		super();
		this.categoria = categoria;
		this.total = total;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	

}
