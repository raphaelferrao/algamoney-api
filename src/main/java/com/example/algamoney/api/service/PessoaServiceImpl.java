package com.example.algamoney.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.exception.ObjectNotFoundException;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaServiceImpl implements PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Override
	public List<Pessoa> listarTodos() {
		return pessoaRepository.findAll();
	}

	@Override
	public Pessoa criar(Pessoa pessoa) {
		Pessoa novaPessoa = pessoaRepository.save(pessoa);
		return novaPessoa;
	}

	@Override
	public Pessoa obterPorId(Long id) {
		Optional<Pessoa> optional = pessoaRepository.findById(id);
		return optional.orElseThrow(() -> new ObjectNotFoundException("Pessoa not found! Id: " + id));
	}
	

}
