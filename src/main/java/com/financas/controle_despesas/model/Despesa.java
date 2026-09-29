package com.financas.controle_despesas.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "despesas")
public class Despesa {

    @Id
    private String id;

    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private String categoria;

    @CreatedDate
    private LocalDateTime createdAt; // O Spring preenche a data atual sozinho

    @CreatedBy
    private String usuarioId; // O Spring preenche o ID do usuário logado sozinho

    public Despesa() {}

    // Abaixo estão os Getters e Setters que o Java estava sentindo falta!
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
}