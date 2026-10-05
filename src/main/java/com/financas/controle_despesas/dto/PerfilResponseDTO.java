package com.financas.controle_despesas.dto;

import com.financas.controle_despesas.model.Usuario;

public record PerfilResponseDTO(
    Double rendaMensalBruta,
    Double limiteGastos,
    String fotoPerfilBase64
) {
    public PerfilResponseDTO(Usuario usuario) {
        this(usuario.getRendaMensalBruta(), usuario.getLimiteGastos(), usuario.getFotoPerfilBase64());
    }
}