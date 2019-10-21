package com.example.algamoney.api.service;

import java.util.List;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.filter.LancamentoFilter;

public interface LancamentoService {

	public List<Lancamento> listarTodos();

	public Lancamento obterPorId(Long id);

	public Lancamento criar(Lancamento lancamento);

	public List<Lancamento> pesquisar(LancamentoFilter lancamentoFilter);

	public void delete(Long id);

}