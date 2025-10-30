package br.com.auramed.interfaces.dto.request;

public class PacienteRequestDTO {
    private Integer idPessoa;
    private Integer idMedicoResponsavel;
    private String nrCartaoSUS;

    // Construtores
    public PacienteRequestDTO() {}

    public PacienteRequestDTO(Integer idPessoa, Integer idMedicoResponsavel, String nrCartaoSUS) {
        this.idPessoa = idPessoa;
        this.idMedicoResponsavel = idMedicoResponsavel;
        this.nrCartaoSUS = nrCartaoSUS;
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
}