package com.financas.controle_despesas.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.financas.controle_despesas.dto.DespesaRequestDTO;
import com.financas.controle_despesas.dto.DespesaResponseDTO;
import com.financas.controle_despesas.dto.ResumoGastosDTO;
import com.financas.controle_despesas.model.Despesa;
import com.financas.controle_despesas.model.Usuario;
import com.financas.controle_despesas.service.DespesaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/despesas")
public class DespesaController {

    private final DespesaService despesaService;

    public DespesaController(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    @PostMapping
    public ResponseEntity<DespesaResponseDTO> criar(@Valid @RequestBody DespesaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(despesaService.criar(dto));
    }

    @GetMapping
    public ResponseEntity<Page<DespesaResponseDTO>> listar(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PageableDefault(size = 10, sort = {"data"}) Pageable paginacao) {

        // 1. O Controller manda o Service buscar os dados paginados e filtrados
        Page<Despesa> despesas = despesaService.listarDespesas(usuarioLogado.getId(), paginacao);

        // 2. Converte a entidade para o formato que vai pra tela (DTO)
        Page<DespesaResponseDTO> pageResponse = despesas.map(DespesaResponseDTO::new);

        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaResponseDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(despesaService.buscarPorId(id));
    }

    @GetMapping("/estatisticas")
    public ResponseEntity<ResumoGastosDTO> obterEstatisticas(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestParam int ano,
            @RequestParam int mes) {
        
        ResumoGastosDTO resumo = despesaService.obterResumoMes(usuarioLogado.getId(), ano, mes);
        return ResponseEntity.ok(resumo);
    }

    // Adicione este endpoint na classe DespesaController
    @GetMapping("/mes")
    public ResponseEntity<List<DespesaResponseDTO>> listarPorMes(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestParam int ano,
            @RequestParam int mes) {
        
        List<DespesaResponseDTO> despesas = despesaService.listarDespesasDoMes(usuarioLogado.getId(), ano, mes);
        return ResponseEntity.ok(despesas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesaResponseDTO> atualizar(
            @PathVariable String id, 
            @Valid @RequestBody DespesaRequestDTO dto) {
        return ResponseEntity.ok(despesaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        despesaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}