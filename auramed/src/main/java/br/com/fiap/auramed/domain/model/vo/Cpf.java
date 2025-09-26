package br.com.fiap.auramed.domain.model.vo;

import br.com.fiap.auramed.domain.exception.DadosInvalidosException;

import java.util.Objects;

public record Cpf(String numero) {
    public Cpf {
        Objects.requireNonNull(numero, "O número do CPF não pode ser nulo.");
        String cpfLimpo = numero.replaceAll("[^0-9]", "");

        if (cpfLimpo.length() != 11) {
            throw new DadosInvalidosException("O CPF deve conter exatamente 11 dígitos.");
        }

        if (cpfLimpo.matches("(\\d)\\1{10}")) {
            throw new DadosInvalidosException("CPF com todos os dígitos iguais é inválido.");
        }
    }
}