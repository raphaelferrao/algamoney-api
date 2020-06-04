package com.example.algamoney.api.repository.listener;

import javax.persistence.PostLoad;

import com.example.algamoney.api.AlgamoneyApiApplication;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.storage.S3;
import com.example.algamoney.api.util.Utils;

public class LancamentoAnexoListener {
	
	@PostLoad
	public void postLoad(Lancamento lancamento) {
		
		if (!Utils.empty(lancamento.getAnexo())) {
			S3 s3 = AlgamoneyApiApplication.getBean(S3.class);
			lancamento.setUrlAnexo(s3.configurarUrl(lancamento.getAnexo()));
		}
	}

}
