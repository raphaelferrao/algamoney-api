package com.example.algamoney.api.service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.dto.LancamentoEstatisticaCategoria;
import com.example.algamoney.api.dto.LancamentoEstatisticaDia;
import com.example.algamoney.api.dto.LancamentoEstatisticaPessoa;
import com.example.algamoney.api.exception.ObjectNotFoundException;
import com.example.algamoney.api.exception.PessoaInexistenteOuInativaException;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.repository.filter.LancamentoFilter;
import com.example.algamoney.api.repository.projection.ResumoLancamento;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class LancamentoServiceImpl implements LancamentoService {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Override
	public List<Lancamento> listarTodos() {
		return lancamentoRepository.findAll();
	}

	@Override
	public Lancamento obterPorId(final Long id) {
		Optional<Lancamento> optional = lancamentoRepository.findById(id);
		return optional.orElseThrow(() -> new ObjectNotFoundException("Lancamento not found! Id: " + id));
	}

	@Override
	public Lancamento criar(final Lancamento lancamento) {
		Optional<Pessoa> optional = pessoaRepository.findById(lancamento.getPessoa().getCodigo());
		if ( (!optional.isPresent()) || (optional.get().isInativo()) ) {
			throw new PessoaInexistenteOuInativaException("Pessoa not found or inactive! Id: " + lancamento.getPessoa().getCodigo());
		}
		
		return lancamentoRepository.save(lancamento);
	}

	@Override
	public Page<Lancamento> pesquisar(final LancamentoFilter lancamentoFilter, final Pageable pageable) {
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}

	@Override
	public void delete(final Long id) {
		this.obterPorId(id);
		lancamentoRepository.deleteById(id);
	}

	@Override
	public Page<ResumoLancamento> resumir(final LancamentoFilter lancamentoFilter, final Pageable pageable) {
		return lancamentoRepository.resumir(lancamentoFilter, pageable);
	}

	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalvo = buscarLancamentoExistente(codigo);
		if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
			validarPessoa(lancamento);
		}

		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");

		return lancamentoRepository.save(lancamentoSalvo);
	}

	private void validarPessoa(Lancamento lancamento) {
		if (lancamento!=null) {
			Optional<Pessoa> optionalPessoa = null;
			if ( (lancamento.getPessoa()!=null) && (lancamento.getPessoa().getCodigo() != null) ) {
				optionalPessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());

				if (!optionalPessoa.isPresent()) {
					throw new PessoaInexistenteOuInativaException();
				}
			}
		}
	}

	private Lancamento buscarLancamentoExistente(Long codigo) {
		Optional<Lancamento> optionalLancamentoSalvo = lancamentoRepository.findById(codigo);
		if (!optionalLancamentoSalvo.isPresent()) {
			throw new IllegalArgumentException();
		}
		return optionalLancamentoSalvo.get();
	}

	@Override
	public List<LancamentoEstatisticaCategoria> porCategoria() {
		return lancamentoRepository.porCategoria(LocalDate.now());
	}
	
	@Override
	public List<LancamentoEstatisticaDia> porDia() {
		return lancamentoRepository.porDia(LocalDate.now());
	}
	
	public byte[] relatorioPorPessoa(LocalDate dataInicio, LocalDate dataFim) throws Exception {
		List<LancamentoEstatisticaPessoa> dados = lancamentoRepository.porPessoa(dataInicio, dataFim);
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("DT_INICIO", Date.valueOf(dataInicio));
		parametros.put("DT_FIM", Date.valueOf(dataFim));
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		
		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/lancamentos-por-pessoa.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.
				fillReport(inputStream, parametros, new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);		
	}

}
