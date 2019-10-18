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

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping
	public ResponseEntity<?> index() {
		List<Categoria> categorias = categoriaService.listarTodos();
		return ResponseEntity.ok(categorias);
	}

	@PostMapping
	public ResponseEntity<?> store(@Valid @RequestBody final Categoria categoria){
		Categoria novaCategoria = categoriaService.criar(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
					.buildAndExpand(novaCategoria.getCodigo()).toUri();
		
		return ResponseEntity.created(uri).body(novaCategoria);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable Integer id) {
		Categoria categoria = categoriaService.obterPorId(id);
		return ResponseEntity.ok(categoria);
	}
	
	
}
