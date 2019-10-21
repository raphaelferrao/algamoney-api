package com.example.algamoney.api.service;

import java.util.List;

import com.example.algamoney.api.model.Lancamento;

public interface LancamentoService {

	public List<Lancamento> listarTodos();

	public Lancamento obterPorId(Long id);

	public Lancamento criar(Lancamento lancamento);

}