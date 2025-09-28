package br.com.fiap.auramed.domain.model.vo;

import br.com.fiap.auramed.domain.exception.DadosInvalidosException;

import java.util.Objects;
import java.util.regex.Pattern;

public record Email(String enderecoEmail) {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public Email {
        if (enderecoEmail == null || enderecoEmail.trim().isEmpty()) {
            throw new DadosInvalidosException("O e-mail não pode ser nulo ou vazio.");
        }

        if (!EMAIL_PATTERN.matcher(enderecoEmail).matches()) {
            throw new DadosInvalidosException("O formato do e-mail é inválido.");
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(enderecoEmail, email.enderecoEmail);
    }

    @Override
    public String toString() {
        return enderecoEmail;
    }
}
