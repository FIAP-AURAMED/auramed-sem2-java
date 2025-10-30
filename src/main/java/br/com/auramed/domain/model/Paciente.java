package br.com.auramed.domain.model;

import br.com.auramed.domain.exception.ValidacaoDeDominioException;
import java.time.LocalDateTime;

public class Paciente {
    private Integer idPessoa;
    private Integer idMedicoResponsavel;
    private String nrCartaoSUS;
    private LocalDateTime dataCadastro;
    private String ativo;

    public Paciente(Integer idPessoa, Integer idMedicoResponsavel, String nrCartaoSUS) {
        this.idPessoa = idPessoa;
        this.idMedicoResponsavel = idMedicoResponsavel;
        this.nrCartaoSUS = nrCartaoSUS;
        this.dataCadastro = LocalDateTime.now();
        this.ativo = "S";

        validarCartaoSUS();
    }

    public void validarCartaoSUS() {
        if (nrCartaoSUS == null || nrCartaoSUS.isBlank()) {
            throw new ValidacaoDeDominioException("Número do Cartão SUS é obrigatório.");
        }

        // Remove caracteres não numéricos
        String cartaoLimpo = nrCartaoSUS.replaceAll("[^\\d]", "");

        if (cartaoLimpo.length() != 15) {
            throw new ValidacaoDeDominioException("Cartão SUS deve conter 15 dígitos numéricos.");
        }

        if (!cartaoLimpo.matches("\\d{15}")) {
            throw new ValidacaoDeDominioException("Cartão SUS deve conter apenas números.");
        }

        // Validação do dígito verificador do Cartão SUS
        if (!validarDigitoVerificadorSUS(cartaoLimpo)) {
            throw new ValidacaoDeDominioException("Cartão SUS inválido - dígito verificador incorreto.");
        }
    }

    private boolean validarDigitoVerificadorSUS(String cartao) {
        try {
            int soma = 0;
            for (int i = 0; i < 14; i++) {
                int digito = Character.getNumericValue(cartao.charAt(i));
                soma += digito * (15 - i);
            }

            int resto = soma % 11;
            int dvCalculado = resto == 0 ? 0 : 11 - resto;
            int dvInformado = Character.getNumericValue(cartao.charAt(14));

            return dvCalculado == dvInformado;
        } catch (Exception e) {
            throw new ValidacaoDeDominioException("Erro na validação do Cartão SUS.");
        }
    }

    // Getters e Setters
    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Integer getIdMedicoResponsavel() {
        return idMedicoResponsavel;
    }

    public void setIdMedicoResponsavel(Integer idMedicoResponsavel) {
        this.idMedicoResponsavel = idMedicoResponsavel;
    }

    public String getNrCartaoSUS() {
        return nrCartaoSUS;
    }

    public void setNrCartaoSUS(String nrCartaoSUS) {
        this.nrCartaoSUS = nrCartaoSUS;
        validarCartaoSUS();
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }
}