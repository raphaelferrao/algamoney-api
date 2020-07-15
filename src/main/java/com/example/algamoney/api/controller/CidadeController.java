package com.example.algamoney.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Cidade;
import com.example.algamoney.api.service.CidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController extends GenericController {
	
	@Autowired
	private CidadeService cidadeService;
	
	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> get(@PathVariable final Long id) {
		Cidade cidade = cidadeService.obterPorId(id);
		return ResponseEntity.ok(cidade);
	}
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> pesquisar(@RequestParam Long codigoEstado) {
		List<Cidade> cidades = cidadeService.listarPorEstado(codigoEstado);
		return ResponseEntity.ok(cidades);
	}

	
}
