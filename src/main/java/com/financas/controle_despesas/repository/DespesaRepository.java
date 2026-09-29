package com.financas.controle_despesas.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.financas.controle_despesas.model.Despesa;

// Estendemos MongoRepository e passamos a Entidade e o tipo do ID (String)
public interface DespesaRepository extends MongoRepository<Despesa, String> {
    Page<Despesa> findAllByUsuarioId(String usuarioId, Pageable paginacao);
}