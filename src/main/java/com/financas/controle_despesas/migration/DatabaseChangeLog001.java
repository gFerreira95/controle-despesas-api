package com.financas.controle_despesas.migration;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;

@ChangeUnit(id = "criar-indice-usuario-id", order = "001", author = "gilson")
public class DatabaseChangeLog001 {

    @Execution
    public void execute(MongoTemplate mongoTemplate) {
        // Usa o driver nativo do MongoDB para criar o índice contornando o aviso de Deprecated
        Document indexKeys = new Document("usuarioId", 1); // 1 = Ordem Crescente (ASC)
        
        mongoTemplate.getCollection("despesas").createIndex(indexKeys);
        
        System.out.println("MIGRATION 001: Índice de usuarioId criado com sucesso!");
    }

    @RollbackExecution
    public void rollback(MongoTemplate mongoTemplate) {
        mongoTemplate.getCollection("despesas").dropIndex("usuarioId_1");
    }
}