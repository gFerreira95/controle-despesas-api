package com.financas.controle_despesas.repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.financas.controle_despesas.model.Despesa;

// Estendemos MongoRepository e passamos a Entidade e o tipo do ID (String)
public interface DespesaRepository extends MongoRepository<Despesa, String> {
    Page<Despesa> findAllByUsuarioId(String usuarioId, Pageable paginacao);
    List<Despesa> findByUsuarioIdAndDataBetween(String usuarioId, LocalDate inicio, LocalDate fim);
}