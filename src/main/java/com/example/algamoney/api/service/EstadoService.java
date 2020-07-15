package com.example.algamoney.api.service;

import java.util.List;

import com.example.algamoney.api.model.Estado;

public interface EstadoService {

	public List<Estado> listarTodos();

	public Estado obterPorId(Long id);

}