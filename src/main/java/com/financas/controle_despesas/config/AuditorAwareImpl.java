package com.financas.controle_despesas.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.financas.controle_despesas.model.Usuario;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // Pega as informações do usuário que o SecurityFilter colocou no Contexto
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.empty(); // Se não tiver ninguém logado, retorna vazio
        }

        // Pega o objeto Usuário logado e devolve o ID dele para o Spring assinar
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        return Optional.of(usuarioLogado.getId());
    }
}