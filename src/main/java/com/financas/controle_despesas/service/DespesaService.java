package com.financas.controle_despesas.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financas.controle_despesas.dto.DespesaRequestDTO;
import com.financas.controle_despesas.dto.DespesaResponseDTO;
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
}