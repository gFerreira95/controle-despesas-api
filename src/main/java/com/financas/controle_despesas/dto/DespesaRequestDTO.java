package com.financas.controle_despesas.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record DespesaRequestDTO(
    @NotBlank(message = "A descrição é obrigatória")
    String descricao,

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    BigDecimal valor,

    @NotNull(message = "A data é obrigatória")
    LocalDate data,

    @NotBlank(message = "A categoria é obrigatória")
    String categoria
) {}