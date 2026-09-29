package com.financas.controle_despesas.model;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection = "usuarios")
public class Usuario implements UserDetails {

    @Id
    private String id;
    private String login;
    private String senha;

    // Construtor vazio para o Spring
    public Usuario() {}

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    
    public void setSenha(String senha) { this.senha = senha; }

    // =========================================================
    // MÉTODOS OBRIGATÓRIOS DA INTERFACE USERDETAILS
    // =========================================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login; // O Spring usa "Username", mas no nosso caso é o email
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}