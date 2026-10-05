package com.financas.controle_despesas.dto;

import java.util.Map;

public record ResumoGastosDTO(
    Double totalMes,
    Map<String, Double> gastosPorCategoria
) {}