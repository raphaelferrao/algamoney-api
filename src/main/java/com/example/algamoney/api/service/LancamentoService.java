package com.example.algamoney.api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.filter.LancamentoFilter;

public interface LancamentoService {

	public List<Lancamento> listarTodos();

	public Lancamento obterPorId(Long id);

	public Lancamento criar(Lancamento lancamento);

	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable);

	public void delete(Long id);

}