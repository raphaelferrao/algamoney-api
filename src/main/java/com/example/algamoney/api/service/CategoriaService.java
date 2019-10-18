package com.example.algamoney.api.service;

import java.util.List;

import com.example.algamoney.api.model.Categoria;

public interface CategoriaService {

	public List<Categoria> listarTodos();

	public Categoria criar(Categoria categoria);

	public Categoria obterPorId(Integer id);

}