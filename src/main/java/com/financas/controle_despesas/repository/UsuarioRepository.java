package com.financas.controle_despesas.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.financas.controle_despesas.model.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    // O Spring Security precisa buscar o usuário pelo email na hora do login
    Usuario findByLogin(String login);
}