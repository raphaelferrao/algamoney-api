package com.example.algamoney.api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.algamoney.api.model.Pessoa;

public interface PessoaService {

	public List<Pessoa> listarTodos();

	public Pessoa criar(Pessoa pessoa);

	public Pessoa obterPorId(Long id);

	public void delete(Long id);

	public Pessoa update(Long id, Pessoa pessoa);

	public void updateAtivo(Long id, Boolean ativo);

	public Page<Pessoa> findByNomeContaining(String nome, Pageable pageable);

}