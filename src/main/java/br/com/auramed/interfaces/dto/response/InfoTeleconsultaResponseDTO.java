package br.com.auramed.interfaces.dto.response;

import java.time.LocalDateTime;

public class InfoTeleconsultaResponseDTO {
    private Integer idInfoTeleconsulta;
    private Integer idPaciente;
    private String cdHabilidadeDigital;
    private String cdCanalLembrete;
    private String inPrecisaCuidador;
    private String inJaFezTele;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;

    // Construtores
    public InfoTeleconsultaResponseDTO() {}

    public InfoTeleconsultaResponseDTO(Integer idInfoTeleconsulta, Integer idPaciente, String cdHabilidadeDigital,
                                       String cdCanalLembrete, String inPrecisaCuidador, String inJaFezTele,
                                       LocalDateTime dataCadastro, LocalDateTime dataAtualizacao) {
        this.idInfoTeleconsulta = idInfoTeleconsulta;
        this.idPaciente = idPaciente;
        this.cdHabilidadeDigital = cdHabilidadeDigital;
        this.cdCanalLembrete = cdCanalLembrete;
        this.inPrecisaCuidador = inPrecisaCuidador;
        this.inJaFezTele = inJaFezTele;
        this.dataCadastro = dataCadastro;
        this.dataAtualizacao = dataAtualizacao;
    }

    // Getters e Setters
    public Integer getIdInfoTeleconsulta() {
        return idInfoTeleconsulta;
    }

    public void setIdInfoTeleconsulta(Integer idInfoTeleconsulta) {
        this.idInfoTeleconsulta = idInfoTeleconsulta;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getCdHabilidadeDigital() {
        return cdHabilidadeDigital;
    }

    public void setCdHabilidadeDigital(String cdHabilidadeDigital) {
        this.cdHabilidadeDigital = cdHabilidadeDigital;
    }

    public String getCdCanalLembrete() {
        return cdCanalLembrete;
    }

    public void setCdCanalLembrete(String cdCanalLembrete) {
        this.cdCanalLembrete = cdCanalLembrete;
    }

    public String getInPrecisaCuidador() {
        return inPrecisaCuidador;
    }

    public void setInPrecisaCuidador(String inPrecisaCuidador) {
        this.inPrecisaCuidador = inPrecisaCuidador;
    }

    public String getInJaFezTele() {
        return inJaFezTele;
    }

    public void setInJaFezTele(String inJaFezTele) {
        this.inJaFezTele = inJaFezTele;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}