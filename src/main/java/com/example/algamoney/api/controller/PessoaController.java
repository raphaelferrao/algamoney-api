package com.example.algamoney.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController extends GenericController {
	
	@Autowired
	private PessoaService pessoaService;
	
	@GetMapping
	public ResponseEntity<?> index() {
		List<Pessoa> pessoas = pessoaService.listarTodos();
		return ResponseEntity.ok(pessoas);
	}

	@PostMapping
	public ResponseEntity<?> store(@Valid @RequestBody final Pessoa pessoa, final HttpServletResponse response){
		Pessoa novaPessoa = pessoaService.criar(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, novaPessoa.getCodigo()));		
		return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable final Long id) {
		Pessoa pessoa = pessoaService.obterPorId(id);
		return ResponseEntity.ok(pessoa);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable final Long id) {
		pessoaService.delete(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable final Long id, @Valid @RequestBody final Pessoa pessoa) {
		Pessoa pessoaAtualizada = pessoaService.update(id, pessoa);
		return ResponseEntity.ok(pessoaAtualizada);
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateAtivo(@PathVariable final Long id, @Valid @RequestBody final Boolean ativo) {
		pessoaService.updateAtivo(id, ativo);
	}
	
}
