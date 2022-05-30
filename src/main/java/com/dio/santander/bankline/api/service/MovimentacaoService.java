package com.dio.santander.bankline.api.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dio.santander.bankline.api.dto.NovaMovimentacao;
import com.dio.santander.bankline.api.model.Correntista;
import com.dio.santander.bankline.api.model.MovimentacaoTipo;
import com.dio.santander.bankline.api.model.Movimentação;
import com.dio.santander.bankline.api.repository.CorrentistaRepository;
import com.dio.santander.bankline.api.repository.MovimentacaoRepository;

@Service
public class MovimentacaoService {
	
	@Autowired
	private MovimentacaoRepository repository;
	
	@Autowired
	private CorrentistaRepository repositoryC;
	
	public void save(NovaMovimentacao novaM) {
		
		Movimentação movimentação = new Movimentação();
		
//		 Double valor = novaM.getTipo() == MovimentacaoTipo.RECEITA ? novaM.getValor() : novaM.getValor() * -1;
		Double valor = novaM.getValor();
		if(novaM.getTipo() == MovimentacaoTipo.DESPESA ) {
			
			valor = valor * -1;
		}
		
		
		movimentação.setDescricao(novaM.getDescricao());
		movimentação.setValor(valor);
		movimentação.setTipo(novaM.getTipo());
		
		movimentação.setData(LocalDateTime.now());
		
		movimentação.setIdConta(novaM.getIdConta());

		Correntista correntista = repositoryC.findById(novaM.getIdConta()).orElse(null);
		if(correntista != null) {
			
			correntista.getConta().setSaldo(correntista.getConta().getSaldo() + valor);
			
			repositoryC.save(correntista);
		}
		
		
		
		repository.save(movimentação);
	}

}
