package com.example.algamoney.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.algamoney.api.repository.listener.LancamentoAnexoListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@EntityListeners(LancamentoAnexoListener.class)
@Entity
public class Lancamento implements Serializable {
	
	private static final long serialVersionUID = 8519716201550217570L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotNull
	@Size(min=5, max=50)
	private String descricao;

	@Column(name = "data_vencimento")
	@NotNull
	private LocalDate dataVencimento;

	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;

	@NotNull
	private BigDecimal valor;

	private String observacao;

	@Enumerated(EnumType.STRING)
	@NotNull
	private TipoLancamento tipo;

	@ManyToOne
	@JoinColumn(name = "codigo_categoria")
	@NotNull
	private Categoria categoria;

	@JsonIgnoreProperties("contatos")
	@ManyToOne
	@JoinColumn(name = "codigo_pessoa")
	@NotNull
	private Pessoa pessoa;
	
	private String anexo;
	
	@Transient
	private String urlAnexo;

	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public TipoLancamento getTipo() {
		return tipo;
	}
	public void setTipo(TipoLancamento tipo) {
		this.tipo = tipo;
	}

	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public String getAnexo() {
		return anexo;
	}
	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}
	
	public String getUrlAnexo() {
		return urlAnexo;
	}
	public void setUrlAnexo(String urlAnexo) {
		this.urlAnexo = urlAnexo;
	}
	
	@JsonIgnore
	public boolean isReceita() {
		return TipoLancamento.RECEITA.equals(tipo);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ( (obj == null) || (getClass() != obj.getClass()) ) {
			return false;
		}
		
		Lancamento other = (Lancamento) obj;
		if (codigo == null) {
			if (other.codigo != null) {
				return false;
			}
		} else if (!codigo.equals(other.codigo)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "Lancamento [codigo=" + codigo + "]";
	}
	
}
