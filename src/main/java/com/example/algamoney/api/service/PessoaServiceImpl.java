package com.example.algamoney.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
		return pessoaRepository.save(pessoa);
	}

	@Override
	public Pessoa obterPorId(Long id) {
		Optional<Pessoa> optional = pessoaRepository.findById(id);
		return optional.orElseThrow(() -> new ObjectNotFoundException("Pessoa not found! Id: " + id));
	}

	@Override
	public void delete(final Long id) {
		this.obterPorId(id);
		pessoaRepository.deleteById(id);
	}

	@Override
	public Pessoa update(final Long id, final Pessoa pessoaAlterada) {
		Pessoa pessoa = this.obterPorId(id);
		BeanUtils.copyProperties(pessoaAlterada, pessoa, "codigo");
		return pessoaRepository.save(pessoa);
	}

	@Override
	public void updateAtivo(final Long id, final Boolean ativo) {
		Pessoa pessoa = this.obterPorId(id);
		pessoa.setAtivo(ativo);
		pessoaRepository.save(pessoa);
	}

	@Override
	public Page<Pessoa> findByNomeContaining(String nome, Pageable pageable) {
		return pessoaRepository.findByNomeContainingIgnoreCase(nome, pageable);
	}

}
