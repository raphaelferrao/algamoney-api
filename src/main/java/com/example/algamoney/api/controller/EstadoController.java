package com.example.algamoney.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Estado;
import com.example.algamoney.api.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController extends GenericController {
	
	@Autowired
	private EstadoService estadoService;
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> index() {
		List<Estado> estados = estadoService.listarTodos();
		return ResponseEntity.ok(estados);
	}

	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> get(@PathVariable final Long id) {
		Estado estado = estadoService.obterPorId(id);
		return ResponseEntity.ok(estado);
	}
	
	
}
