package com.example.algamoney.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.exception.ObjectNotFoundException;
import com.example.algamoney.api.model.Estado;
import com.example.algamoney.api.repository.EstadoRepository;

@Service
public class EstadoServiceImpl implements EstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;

	@Override
	public List<Estado> listarTodos() {
		return estadoRepository.findAll();
	}

	@Override
	public Estado obterPorId(Long id) {
		Optional<Estado> optional = estadoRepository.findById(id);
		return optional.orElseThrow(() -> new ObjectNotFoundException("Estado not found! Id: " + id));
	}
	

}
