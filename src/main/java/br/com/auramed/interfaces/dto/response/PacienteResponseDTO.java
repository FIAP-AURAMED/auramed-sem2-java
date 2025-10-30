package br.com.auramed.interfaces.dto.response;

import java.time.LocalDateTime;

public class PacienteResponseDTO {
    private Integer idPessoa;
    private Integer idMedicoResponsavel;
    private String nrCartaoSUS;
    private LocalDateTime dataCadastro;
    private String ativo;

    // Construtores
    public PacienteResponseDTO() {}

    public PacienteResponseDTO(Integer idPessoa, Integer idMedicoResponsavel, String nrCartaoSUS,
                               LocalDateTime dataCadastro, String ativo) {
        this.idPessoa = idPessoa;
        this.idMedicoResponsavel = idMedicoResponsavel;
        this.nrCartaoSUS = nrCartaoSUS;
        this.dataCadastro = dataCadastro;
        this.ativo = ativo;
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