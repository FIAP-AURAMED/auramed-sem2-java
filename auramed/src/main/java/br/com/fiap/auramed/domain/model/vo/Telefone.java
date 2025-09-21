package br.com.fiap.auramed.domain.model.vo;

import java.util.Objects;

public record Telefone(String numero) {
    public Telefone {
        Objects.requireNonNull(numero, "O número de telefone não pode ser nulo.");
        String numeroLimpo = numero.replaceAll("[^0-9]", "");

        if (numeroLimpo.length() < 10 || numeroLimpo.length() > 11) {
            throw new IllegalArgumentException("O número de telefone deve ter 10 ou 11 dígitos (com DDD).");
        }
    }
}