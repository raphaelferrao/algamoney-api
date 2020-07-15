package com.example.algamoney.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.exception.ObjectNotFoundException;
import com.example.algamoney.api.model.Cidade;
import com.example.algamoney.api.repository.CidadeRepository;

@Service
public class CidadeServiceImpl implements CidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;

	@Override
	public List<Cidade> listarTodos() {
		return cidadeRepository.findAll();
	}

	@Override
	public Cidade obterPorId(Long id) {
		Optional<Cidade> optional = cidadeRepository.findById(id);
		return optional.orElseThrow(() -> new ObjectNotFoundException("Cidade not found! Id: " + id));
	}

	@Override
	public List<Cidade> listarPorEstado(Long codigoEstado) {
		return cidadeRepository.findByEstadoCodigo(codigoEstado);
	}
	

}
