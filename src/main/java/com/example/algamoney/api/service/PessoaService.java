package com.example.algamoney.api.service;

import java.util.List;

import com.example.algamoney.api.model.Pessoa;

public interface PessoaService {

	public List<Pessoa> listarTodos();

	public Pessoa criar(Pessoa pessoa);

	public Pessoa obterPorId(Long id);

	public void delete(Long id);

	public Pessoa update(Long id, Pessoa pessoa);

	public void updateAtivo(Long id, Boolean ativo);

}