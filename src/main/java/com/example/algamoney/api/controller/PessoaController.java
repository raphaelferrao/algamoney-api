package com.example.algamoney.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;
	
	@GetMapping
	public ResponseEntity<?> index() {
		List<Pessoa> pessoas = pessoaService.listarTodos();
		return ResponseEntity.ok(pessoas);
	}

	@PostMapping
	public ResponseEntity<?> store(@Valid @RequestBody final Pessoa pessoa){
		Pessoa novaPessoa = pessoaService.criar(pessoa);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
					.buildAndExpand(novaPessoa.getCodigo()).toUri();
		
		return ResponseEntity.created(uri).body(novaPessoa);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable Long id) {
		Pessoa pessoa = pessoaService.obterPorId(id);
		return ResponseEntity.ok(pessoa);
	}
	
	
}
