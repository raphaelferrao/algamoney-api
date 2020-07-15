package com.example.algamoney.api.service;

import java.util.List;

import com.example.algamoney.api.model.Cidade;

public interface CidadeService {

	public List<Cidade> listarTodos();

	public Cidade obterPorId(Long id);

	public List<Cidade> listarPorEstado(Long codigoEstado);

}