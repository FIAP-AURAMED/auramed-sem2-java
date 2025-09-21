package br.com.fiap.auramed.domain.model.vo;

import java.util.Objects;

public record Endereco(
        String logradouro,
        String numero,
        String complemento, // Optional field
        String bairro,
        String cidade,
        String uf,
        String cep
) {

    public Endereco {
        Objects.requireNonNull(logradouro, "Logradouro não pode ser nulo.");
        Objects.requireNonNull(numero, "Número não pode ser nulo.");
        Objects.requireNonNull(bairro, "Bairro não pode ser nulo.");
        Objects.requireNonNull(cidade, "Cidade não pode ser nula.");
        Objects.requireNonNull(uf, "UF não pode ser nulo.");
        Objects.requireNonNull(cep, "CEP não pode ser nulo.");

        if (uf.length() != 2) {
            throw new IllegalArgumentException("UF deve ter exatamente 2 caracteres.");
        }

        String cepLimpo = cep.replaceAll("[^0-9]", "");
        if (cepLimpo.length() != 8) {
            throw new IllegalArgumentException("CEP deve conter 8 dígitos.");
        }
    }

    public Endereco(String logradouro, String numero, String bairro, String cidade, String uf, String cep) {
        this(logradouro, numero, null, bairro, cidade, uf, cep);
    }
}