package com.financas.controle_despesas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.mongock.runner.springboot.EnableMongock;

@SpringBootApplication
@EnableMongock

public class ControleDespesasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleDespesasApplication.class, args);
	}

}
