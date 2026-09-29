package com.financas.controle_despesas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica a regra para todas as rotas da API
                .allowedOrigins("http://localhost:4200", "https://front-despesas-app-v01.vercel.app/") // Porta padrão onde o Angular roda
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Verbos liberados
                .allowedHeaders("*") // Libera envio de cabeçalhos (como tokens de autenticação no futuro)
                .allowCredentials(true);
    }
}