package com.example.algamoney.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Lancamento_;
import com.example.algamoney.api.repository.filter.LancamentoFilter;
import com.example.algamoney.api.util.Utils;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Lancamento> filtrar(final LancamentoFilter lancamentoFilter, final Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteriaQuery = criteriaBuilder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		Predicate[] restrictions = criarRestricoes(lancamentoFilter, criteriaBuilder, root);
		criteriaQuery.where(restrictions);
		
		TypedQuery<Lancamento> query = entityManager.createQuery(criteriaQuery);
		adicionarRestricoesPaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, obterTotal(lancamentoFilter));
	}

	private Predicate[] criarRestricoes(final LancamentoFilter lancamentoFilter, final CriteriaBuilder criteriaBuilder,
			final Root<Lancamento> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if (lancamentoFilter!=null) {
			if (!Utils.empty(lancamentoFilter.getDescricao())) {
				predicates.add(
					criteriaBuilder.like(criteriaBuilder.lower(root.get(Lancamento_.descricao)), 
							"%" + lancamentoFilter.getDescricao().trim().toLowerCase() + "%")
				);
			}
			
			if (lancamentoFilter.getDataVencimentoDe()!=null) {
				predicates.add(
					criteriaBuilder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), 
							lancamentoFilter.getDataVencimentoDe())
				);
			}
			
			if (lancamentoFilter.getDataVencimentoPara()!=null) {
				predicates.add(
					criteriaBuilder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), 
							lancamentoFilter.getDataVencimentoPara())
				);
			}
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void adicionarRestricoesPaginacao(final TypedQuery<Lancamento> query, final Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	

	private Long obterTotal(final LancamentoFilter lancamentoFilter) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		Predicate[] restrictions = criarRestricoes(lancamentoFilter, criteriaBuilder, root);
		criteriaQuery.where(restrictions);
		
		criteriaQuery.select(criteriaBuilder.count(root));
		
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}

}
