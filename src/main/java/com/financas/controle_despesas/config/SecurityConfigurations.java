package com.financas.controle_despesas.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.financas.controle_despesas.service.AutenticacaoService;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityFilter securityFilter) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Mantido apenas uma vez
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    // 1. Liberta explicitamente todas as requisições de Preflight (OPTIONS)
                    req.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll(); 
                    
                    req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll();
                    req.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter,
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://front-despesas-app.vercel.app"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        
        // 2. Permite a passagem de credenciais (Tokens/Headers protegidos)
        configuration.setAllowCredentials(true); 
        
        // 3. Diz ao navegador para guardar esta permissão CORS por 1 hora (3600 segundos), evitando o Network Error
        configuration.setMaxAge(3600L); 
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); 
        
        return source;
    }
    
    // Ensina o Spring a injetar o AuthenticationManager 
    // AuthController
    @Bean
    public AuthenticationManager authenticationManager(AutenticacaoService autenticacaoService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(autenticacaoService);
        authProvider.setPasswordEncoder(passwordEncoder);
        
        return new ProviderManager(authProvider);
    }
    // BCrypt (hash seguro) para salvar/ler as senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}