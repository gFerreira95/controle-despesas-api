
package com.financas.controle_despesas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoAuditing(auditorAwareRef = "auditorAwareImpl")
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        // Aqui nós criamos a conexão manualmente, ignorando qualquer autoconfiguração do Spring
        return MongoClients.create("mongodb+srv://gilsonferreiradepaula_db_user:ZUu4Irymy6PHKwm8@cluster0.kmznerb.mongodb.net/despesasdb?retryWrites=true&w=majority");
    }
}