package com.example.algamoney.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.exception.ObjectNotFoundException;
import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Override
	public List<Categoria> listarTodos() {
		return categoriaRepository.findAll();
	}

	@Override
	public Categoria criar(Categoria categoria) {
		Categoria novaCategoria = categoriaRepository.save(categoria);
		return novaCategoria;
	}

	@Override
	public Categoria obterPorId(Integer id) {
		Optional<Categoria> optional = categoriaRepository.findById(id);
		return optional.orElseThrow(() -> new ObjectNotFoundException("Graph not found! Id: " + id));
	}
	

}
