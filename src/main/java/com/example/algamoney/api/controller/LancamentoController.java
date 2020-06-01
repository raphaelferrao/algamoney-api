package com.example.algamoney.api.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.dto.LancamentoEstatisticaCategoria;
import com.example.algamoney.api.dto.LancamentoEstatisticaDia;
import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.exception.ErrorDTO;
import com.example.algamoney.api.exception.PessoaInexistenteOuInativaException;
import com.example.algamoney.api.exception.StandardErrorDTO;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.filter.LancamentoFilter;
import com.example.algamoney.api.repository.projection.ResumoLancamento;
import com.example.algamoney.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController extends GenericController {
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@GetMapping
	@Secured({ "ROLE_PESQUISAR_LANCAMENTO" })
	public Page<Lancamento> index(final LancamentoFilter lancamentoFilter, final Pageable pageable) {
		return lancamentoService.pesquisar(lancamentoFilter, pageable);
	}
	
	@GetMapping(params = "resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<ResumoLancamento> summarize(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoService.resumir(lancamentoFilter, pageable);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<?> get(@PathVariable final Long id) {
		Lancamento lancamento = lancamentoService.obterPorId(id);
		return ResponseEntity.ok(lancamento);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<?> store(@Valid @RequestBody final Lancamento lancamento, final HttpServletResponse response){
		Lancamento novoLancamento = lancamentoService.criar(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, novoLancamento.getCodigo()));		
		return ResponseEntity.status(HttpStatus.CREATED).body(novoLancamento);
	}
	
	@ExceptionHandler({PessoaInexistenteOuInativaException.class}) 
	public ResponseEntity<StandardErrorDTO> handlePessoaInexistenteOuInativaException(
			final PessoaInexistenteOuInativaException exception, final HttpServletRequest httpServletRequest) {
		
		final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		
		final String msgError = messageSource.getMessage("msg.error.pessoa.inexistente.ou.inativa", null, LocaleContextHolder.getLocale());
		
		final StandardErrorDTO standardErrorResponse = new StandardErrorDTO(Calendar.getInstance().getTimeInMillis(), httpStatus.value(), 
				msgError, httpServletRequest.getRequestURI());
		
		if (exception != null) {
			final String msg = Optional.ofNullable(ExceptionUtils.getRootCauseMessage(exception)).orElse(exception.toString());
			standardErrorResponse.getMessages().add(new ErrorDTO(msg, msgError));
		}
		
		return ResponseEntity.status(httpStatus).body(standardErrorResponse);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	public void delete(@PathVariable final Long id) {
		lancamentoService.delete(id);
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO')")
	public ResponseEntity<Lancamento> update(@PathVariable Long codigo, @Valid @RequestBody Lancamento lancamento) {
		try {
			Lancamento lancamentoSalvo = lancamentoService.atualizar(codigo, lancamento);
			return ResponseEntity.ok(lancamentoSalvo);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/estatisticas/por-categoria")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public List<LancamentoEstatisticaCategoria> porCategoria(){
		return this.lancamentoService.porCategoria();
	}
	
	@GetMapping("/estatisticas/por-dia")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public List<LancamentoEstatisticaDia> porDia(){
		return this.lancamentoService.porDia();
	}
}
