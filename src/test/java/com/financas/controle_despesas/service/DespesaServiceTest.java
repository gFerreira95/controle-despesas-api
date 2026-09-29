package com.financas.controle_despesas.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.financas.controle_despesas.model.Despesa;
import com.financas.controle_despesas.repository.DespesaRepository;

@ExtendWith(MockitoExtension.class)
class DespesaServiceTest {

    // 1. "Banco de Dados Falso" (Mock)
    @Mock
    private DespesaRepository repository;

    // 2. Injeta o banco falso dentro do nosso Service Real
    @InjectMocks
    private DespesaService service;

    @Test
    @DisplayName("Deve retornar uma página de despesas filtrada pelo ID do usuário")
    void deveListarDespesasPorUsuarioComPaginacao() {
        // Arrange (Preparação do cenário)
        String usuarioId = "id-do-gilson";
        Pageable paginacao = PageRequest.of(0, 10);
        
        Despesa despesaMock = new Despesa();
        despesaMock.setId("despesa-1");
        despesaMock.setDescricao("Compra de Equipamento");
        despesaMock.setUsuarioId(usuarioId);
        
        Page<Despesa> paginaMock = new PageImpl<>(List.of(despesaMock));

        // Ensinamos o mock: "Quando chamarem esse método, devolva essa página falsa"
        when(repository.findAllByUsuarioId(eq(usuarioId), any(Pageable.class))).thenReturn(paginaMock);

        // Act (Ação: O que estamos testando de verdade)
        Page<Despesa> resultado = service.listarDespesas(usuarioId, paginacao);

        // Assert (Verificação: Garantindo que a arquitetura não foi violada)
        assertNotNull(resultado, "A página não deveria ser nula");
        assertEquals(1, resultado.getTotalElements(), "Deveria ter 1 item na página");
        assertEquals("Compra de Equipamento", resultado.getContent().get(0).getDescricao());
        
        // O teste mais importante: Garante que o Service repassou o ID do usuário para o Repository
        verify(repository).findAllByUsuarioId(usuarioId, paginacao);
    }
}