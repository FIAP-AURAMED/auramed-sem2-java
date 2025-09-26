package br.com.fiap.auramed.domain.model.vo;

import br.com.fiap.auramed.domain.exception.DadosInvalidosException;

import java.time.LocalDate;
import java.util.Objects;

public record DataNascimento(LocalDate data) {
    public DataNascimento {
        Objects.requireNonNull(data, "A data de nascimento não pode ser nula.");

        if (data.isAfter(LocalDate.now())) {
            throw new DadosInvalidosException("A data de nascimento não pode ser no futuro.");
        }
    }
}