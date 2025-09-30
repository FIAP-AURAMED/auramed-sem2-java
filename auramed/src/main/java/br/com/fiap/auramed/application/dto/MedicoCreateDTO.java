package br.com.fiap.auramed.application.dto;

import java.time.LocalDate;

public record MedicoCreateDTO(
        String nomeCompleto,
        String email,
        String cpf,
        LocalDate dataNascimento,
        String genero,
        String telefone,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        String cep,
        String crmNumero,
        String crmUf,
        String senha
) {}