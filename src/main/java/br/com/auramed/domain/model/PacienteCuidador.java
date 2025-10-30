package br.com.auramed.domain.model;

import br.com.auramed.domain.exception.ValidacaoDeDominioException;
import java.time.LocalDateTime;

public class PacienteCuidador {
    private Integer idPaciente;
    private Integer idCuidador;
    private String tipoRelacionamento;
    private LocalDateTime dataAssociacao;
    private String ativo;

    public PacienteCuidador(Integer idPaciente, Integer idCuidador) {
        this.idPaciente = idPaciente;
        this.idCuidador = idCuidador;
        this.dataAssociacao = LocalDateTime.now();
        this.ativo = "S";
    }

    public PacienteCuidador(Integer idPaciente, Integer idCuidador, String tipoRelacionamento) {
        this.idPaciente = idPaciente;
        this.idCuidador = idCuidador;
        this.tipoRelacionamento = tipoRelacionamento;
        this.dataAssociacao = LocalDateTime.now();
        this.ativo = "S";

        validarTipoRelacionamento();
    }

    public void validarTipoRelacionamento() {
        if (tipoRelacionamento == null || tipoRelacionamento.isBlank()) {
            return;
        }

        if (tipoRelacionamento.length() > 50) {
            throw new ValidacaoDeDominioException("Tipo de relacionamento deve ter no máximo 50 caracteres.");
        }
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
        validarTipoRelacionamento();
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