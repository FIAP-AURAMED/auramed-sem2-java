package br.com.fiap.auramed.domain.model.vo;

import java.util.Objects;

public record RegistroProfissional(String numero, Uf uf) {
    public RegistroProfissional {
        Objects.requireNonNull(numero, "O número do registro não pode ser nulo.");
        Objects.requireNonNull(uf, "A UF do registro não pode ser nula.");

        if (numero.trim().isEmpty() || !numero.matches("\\d+")) {
            throw new IllegalArgumentException("O número do registro deve conter apenas dígitos.");
        }
    }

    public String getRegistroFormatado() {
        return this.numero + "/" + this.uf.name();
    }
}