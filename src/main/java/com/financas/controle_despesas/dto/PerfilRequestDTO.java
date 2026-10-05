package com.financas.controle_despesas.dto;

public record PerfilRequestDTO(
    Double rendaMensalBruta,
    Double limiteGastos,
    String fotoPerfilBase64
) {}