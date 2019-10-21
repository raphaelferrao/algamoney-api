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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.exception.ErrorDTO;
import com.example.algamoney.api.exception.PessoaInexistenteOuInativaException;
import com.example.algamoney.api.exception.StandardErrorDTO;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.filter.LancamentoFilter;
import com.example.algamoney.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController extends GenericController {
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@GetMapping
	public ResponseEntity<?> index(final LancamentoFilter lancamentoFilter) {
		List<Lancamento> lancamentos = null;
		if (lancamentoFilter==null) {
			lancamentos = lancamentoService.listarTodos();
		} else {
			lancamentos = lancamentoService.pesquisar(lancamentoFilter);
		}
		return ResponseEntity.ok(lancamentos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable final Long id) {
		Lancamento lancamento = lancamentoService.obterPorId(id);
		return ResponseEntity.ok(lancamento);
	}
	
	@PostMapping
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
	
}
