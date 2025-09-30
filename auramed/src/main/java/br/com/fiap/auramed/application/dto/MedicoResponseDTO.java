package br.com.fiap.auramed.application.dto;

import java.time.LocalDate;

public record MedicoResponseDTO(
        Long id,
        String nomeCompleto,
        String email,
        String crmFormatado,
        String telefone,
        LocalDate dataNascimento,
        String genero,
        EnderecoDTO endereco
) {
    public record EnderecoDTO(
            String logradouro,
            String numero,
            String complemento,
            String bairro,
            String cidade,
            String uf,
            String cep
    ) {}
}