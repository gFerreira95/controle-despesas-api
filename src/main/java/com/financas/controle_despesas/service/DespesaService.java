package com.financas.controle_despesas.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financas.controle_despesas.dto.DespesaRequestDTO;
import com.financas.controle_despesas.dto.DespesaResponseDTO;
import com.financas.controle_despesas.dto.ResumoGastosDTO;
import com.financas.controle_despesas.model.Despesa;
import com.financas.controle_despesas.repository.DespesaRepository;



@Service
public class DespesaService {

    private final DespesaRepository repository;

    // Injeção de dependência via construtor
    public DespesaService(DespesaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public DespesaResponseDTO criar(DespesaRequestDTO dto) {
        Despesa despesa = new Despesa();
        despesa.setDescricao(dto.descricao());
        despesa.setValor(dto.valor());
        despesa.setData(dto.data());
        despesa.setCategoria(dto.categoria());

        Despesa salva = repository.save(despesa);
        return new DespesaResponseDTO(salva);
    }

    public List<DespesaResponseDTO> listarTodas() {
        return repository.findAll().stream()
                .map(DespesaResponseDTO::new) // Usa o construtor que criamos no Record
                .toList();
    }

    public DespesaResponseDTO buscarPorId(String id) {
        Despesa despesa = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada com o ID: " + id));
        return new DespesaResponseDTO(despesa);
    }

    public Page<Despesa> listarDespesas(String usuarioId, Pageable paginacao) {
        return repository.findAllByUsuarioId(usuarioId, paginacao);
    }

    @Transactional
    public DespesaResponseDTO atualizar(String id, DespesaRequestDTO dto) {
        Despesa despesa = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada com o ID: " + id));

        despesa.setDescricao(dto.descricao());
        despesa.setValor(dto.valor());
        despesa.setData(dto.data());
        despesa.setCategoria(dto.categoria());

        Despesa salva = repository.save(despesa);
        return new DespesaResponseDTO(salva);
    }

    @Transactional
    public void deletar(String id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Despesa não encontrada com o ID: " + id);
        }
        repository.deleteById(id);
    }

    public ResumoGastosDTO obterResumoMes(String usuarioId, int ano, int mes) {
        YearMonth anoMes = YearMonth.of(ano, mes);
        LocalDate inicio = anoMes.atDay(1);
        LocalDate fim = anoMes.atEndOfMonth();

        // Trocado para 'this.repository' (ou 'repository'), que é o nome padrão da sua injeção
        List<Despesa> despesasDoMes = this.repository.findByUsuarioIdAndDataBetween(usuarioId, inicio, fim);

        // Converte o BigDecimal para double explicitamente com getValor().doubleValue()
        Double totalMes = despesasDoMes.stream()
                .mapToDouble(despesa -> despesa.getValor().doubleValue())
                .sum();

        // Converte o BigDecimal para double também no agrupamento
        Map<String, Double> porCategoria = despesasDoMes.stream()
                .collect(Collectors.groupingBy(
                        despesa -> despesa.getCategoria(),
                        Collectors.summingDouble(despesa -> despesa.getValor().doubleValue())
                ));

        return new ResumoGastosDTO(totalMes, porCategoria);
    }

    public List<DespesaResponseDTO> listarDespesasDoMes(String usuarioId, int ano, int mes) {
        YearMonth anoMes = YearMonth.of(ano, mes);
        LocalDate inicio = anoMes.atDay(1);
        LocalDate fim = anoMes.atEndOfMonth();

        List<Despesa> despesas = this.repository.findByUsuarioIdAndDataBetween(usuarioId, inicio, fim);

        // Converte as entidades Despesa para DTOs antes de devolver ao Front-end
        return despesas.stream()
                .map(DespesaResponseDTO::new) 
                .collect(Collectors.toList());
    }
}