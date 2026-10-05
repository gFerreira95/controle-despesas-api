package com.financas.controle_despesas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financas.controle_despesas.dto.PerfilRequestDTO;
import com.financas.controle_despesas.dto.PerfilResponseDTO;
import com.financas.controle_despesas.model.Usuario;
import com.financas.controle_despesas.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/perfil")
    public ResponseEntity<PerfilResponseDTO> buscarPerfil(@AuthenticationPrincipal Usuario usuarioLogado) {
        return ResponseEntity.ok(usuarioService.buscarPerfil(usuarioLogado));
    }

    @PutMapping("/perfil")
    public ResponseEntity<PerfilResponseDTO> atualizarPerfil(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @Valid @RequestBody PerfilRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizarPerfil(usuarioLogado, dto));
    }
}