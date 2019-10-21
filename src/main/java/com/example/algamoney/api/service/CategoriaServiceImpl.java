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
		return categoriaRepository.save(categoria);
	}

	@Override
	public Categoria obterPorId(Long id) {
		Optional<Categoria> optional = categoriaRepository.findById(id);
		return optional.orElseThrow(() -> new ObjectNotFoundException("Categoria not found! Id: " + id));
	}
	

}
