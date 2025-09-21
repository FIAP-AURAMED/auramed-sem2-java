package br.com.fiap.auramed.domain.model.vo;

import java.util.regex.Pattern;

public record NomeCompleto(String valor) {

    private static final Pattern INVALID_CHARS_PATTERN = Pattern.compile("[^\\p{L}\\s]");
    public NomeCompleto {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome completo não pode ser nulo ou vazio.");
        }

        String nomeLimpo = valor.trim();

        if (nomeLimpo.length() < 3) {
            throw new IllegalArgumentException("O nome completo deve ter no mínimo 3 caracteres.");
        }

        if (INVALID_CHARS_PATTERN.matcher(nomeLimpo).find()) {
            throw new IllegalArgumentException("O nome completo não pode conter números ou caracteres especiais.");
        }

        if (!nomeLimpo.contains(" ")) {
            throw new IllegalArgumentException("O nome completo deve conter nome e sobrenome.");
        }
    }

    public String getPrimeiroNome() {
        return valor.split(" ")[0];
    }

    public String getUltimoSobrenome() {
        String[] partes = valor.split(" ");
        return partes[partes.length - 1];
    }
}