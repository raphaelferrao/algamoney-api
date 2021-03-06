package com.example.algamoney.api.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.algamoney.api.dto.LancamentoEstatisticaCategoria;
import com.example.algamoney.api.dto.LancamentoEstatisticaDia;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.filter.LancamentoFilter;
import com.example.algamoney.api.repository.projection.ResumoLancamento;

public interface LancamentoService {

	public List<Lancamento> listarTodos();

	public Lancamento obterPorId(Long id);

	public Lancamento criar(Lancamento lancamento);

	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable);

	public void delete(Long id);

	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);

	public Lancamento atualizar(Long codigo, Lancamento lancamento);

	public List<LancamentoEstatisticaCategoria> porCategoria();
	
	public List<LancamentoEstatisticaDia> porDia();
	
	public byte[] relatorioPorPessoa(LocalDate dataInicio, LocalDate dataFim) throws Exception;

}