package com.financas.controle_despesas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financas.controle_despesas.dto.AuthDTO;
import com.financas.controle_despesas.dto.TokenResponseDTO;
import com.financas.controle_despesas.model.Usuario;
import com.financas.controle_despesas.repository.UsuarioRepository;
import com.financas.controle_despesas.service.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager manager;
    private final UsuarioRepository repository;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager manager, UsuarioRepository repository, TokenService tokenService) {
        this.manager = manager;
        this.repository = repository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid AuthDTO dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);
        
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        
        return ResponseEntity.ok(new TokenResponseDTO(tokenJWT));
    }

    @PostMapping("/registrar")
    public ResponseEntity<Void> registrar(@RequestBody @Valid AuthDTO dados) {
        if (this.repository.findByLogin(dados.login()) != null) {
            return ResponseEntity.badRequest().build(); // Retorna Erro 400 se o login já existir
        }

        // Criptografa a senha antes de salvar no banco
        String senhaCriptografada = new BCryptPasswordEncoder().encode(dados.senha());
        Usuario novoUsuario = new Usuario(dados.login(), senhaCriptografada);
        
        this.repository.save(novoUsuario);
        
        return ResponseEntity.ok().build();
    }
}