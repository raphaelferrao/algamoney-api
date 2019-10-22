package com.example.algamoney.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController extends GenericController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_PESQUISAR_CATEGORIA')")
	public ResponseEntity<?> index() {
		List<Categoria> categorias = categoriaService.listarTodos();
		return ResponseEntity.ok(categorias);
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<?> store(@Valid @RequestBody final Categoria categoria, final HttpServletResponse response){
		Categoria novaCategoria = categoriaService.criar(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, novaCategoria.getCodigo()));		
		return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_PESQUISAR_CATEGORIA')")
	public ResponseEntity<?> get(@PathVariable final Long id) {
		Categoria categoria = categoriaService.obterPorId(id);
		return ResponseEntity.ok(categoria);
	}
	
	
}
