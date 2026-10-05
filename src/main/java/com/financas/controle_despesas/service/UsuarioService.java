package com.financas.controle_despesas.service;

import org.springframework.stereotype.Service;

import com.financas.controle_despesas.dto.PerfilRequestDTO;
import com.financas.controle_despesas.dto.PerfilResponseDTO;
import com.financas.controle_despesas.model.Usuario;
import com.financas.controle_despesas.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public PerfilResponseDTO buscarPerfil(Usuario usuario) {
        return new PerfilResponseDTO(usuario);
    }

    public PerfilResponseDTO atualizarPerfil(Usuario usuario, PerfilRequestDTO dto) {
        usuario.setRendaMensalBruta(dto.rendaMensalBruta());
        usuario.setLimiteGastos(dto.limiteGastos());
        usuario.setFotoPerfilBase64(dto.fotoPerfilBase64());
        
        repository.save(usuario);
        
        return new PerfilResponseDTO(usuario);
    }
}