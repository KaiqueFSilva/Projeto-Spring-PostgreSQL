package com.dio.santander.bankline.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dio.santander.bankline.api.model.Movimentação;

public interface MovimentacaoRepository extends JpaRepository<Movimentação, Integer> {

}
