package com.financas.controle_despesas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Trata os erros de "Não Encontrado" (RuntimeExceptions do Service)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        String detalhe = ex.getMessage() == null ? "Recurso não encontrado" : ex.getMessage();
        return response(HttpStatus.NOT_FOUND, "Recurso não encontrado", detalhe);
    }

    // 2. Trata os erros de Validação (os @NotBlank, @NotNull, etc., do seu DTO)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        // Pega todos os campos que deram erro e junta numa String bonitinha
        String mensagensDeErro = ex.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return response(HttpStatus.BAD_REQUEST, "Erro de validação nos dados enviados", mensagensDeErro);
    }

    private ResponseEntity<Map<String, String>> response(HttpStatus status, String title, String detail) {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("title", title);
        body.put("detail", detail);
        return ResponseEntity.status(status).body(body);
    }
}