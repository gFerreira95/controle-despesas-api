package com.financas.controle_despesas.dto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.financas.controle_despesas.model.Despesa;

public record DespesaResponseDTO(
    String id, 
    String descricao,
    BigDecimal valor,
    LocalDate data,
    String categoria,
    LocalDateTime createdAt,
    String usuarioId
) {
    // Construtor utilitário para converter Entidade -> DTO facilmente
    public DespesaResponseDTO(Despesa despesa) {
        this(despesa.getId(), despesa.getDescricao(), despesa.getValor(), despesa.getData(), despesa.getCategoria(), despesa.getCreatedAt(), despesa.getUsuarioId());
    }
}