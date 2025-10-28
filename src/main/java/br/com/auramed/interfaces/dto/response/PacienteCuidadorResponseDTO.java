package br.com.auramed.interfaces.dto.response;

import java.time.LocalDateTime;

public class PacienteCuidadorResponseDTO {
    private Integer idPaciente;
    private Integer idCuidador;
    private String tipoRelacionamento;
    private LocalDateTime dataAssociacao;
    private String ativo;

    // Construtores
    public PacienteCuidadorResponseDTO() {}

    public PacienteCuidadorResponseDTO(Integer idPaciente, Integer idCuidador, String tipoRelacionamento,
                                       LocalDateTime dataAssociacao, String ativo) {
        this.idPaciente = idPaciente;
        this.idCuidador = idCuidador;
        this.tipoRelacionamento = tipoRelacionamento;
        this.dataAssociacao = dataAssociacao;
        this.ativo = ativo;
    }

    // Getters e Setters
    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Integer getIdCuidador() {
        return idCuidador;
    }

    public void setIdCuidador(Integer idCuidador) {
        this.idCuidador = idCuidador;
    }

    public String getTipoRelacionamento() {
        return tipoRelacionamento;
    }

    public void setTipoRelacionamento(String tipoRelacionamento) {
        this.tipoRelacionamento = tipoRelacionamento;
    }

    public LocalDateTime getDataAssociacao() {
        return dataAssociacao;
    }

    public void setDataAssociacao(LocalDateTime dataAssociacao) {
        this.dataAssociacao = dataAssociacao;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }
}