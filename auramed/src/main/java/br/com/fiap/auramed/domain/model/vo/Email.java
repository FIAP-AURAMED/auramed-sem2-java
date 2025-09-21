package br.com.fiap.auramed.domain.model.vo;

import java.util.Objects;
import java.util.regex.Pattern;

public record Email(String address) {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public Email {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("O e-mail não pode ser nulo ou vazio.");
        }

        if (!EMAIL_PATTERN.matcher(address).matches()) {
            throw new IllegalArgumentException("O formato do e-mail é inválido.");
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(address, email.address);
    }

    @Override
    public String toString() {
        return address;
    }
}
