package br.com.auramed.interfaces.dto.request;

public class PacienteCuidadorRequestDTO {
    private Integer idPaciente;
    private Integer idCuidador;
    private String tipoRelacionamento;

    // Construtores
    public PacienteCuidadorRequestDTO() {}

    public PacienteCuidadorRequestDTO(Integer idPaciente, Integer idCuidador, String tipoRelacionamento) {
        this.idPaciente = idPaciente;
        this.idCuidador = idCuidador;
        this.tipoRelacionamento = tipoRelacionamento;
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
}