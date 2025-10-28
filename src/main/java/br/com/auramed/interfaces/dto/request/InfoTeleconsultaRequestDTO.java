package br.com.auramed.interfaces.dto.request;

public class InfoTeleconsultaRequestDTO {
    private Integer idPaciente;
    private String cdHabilidadeDigital;
    private String cdCanalLembrete;
    private String inPrecisaCuidador;
    private String inJaFezTele;

    // Construtores
    public InfoTeleconsultaRequestDTO() {}

    public InfoTeleconsultaRequestDTO(Integer idPaciente, String cdHabilidadeDigital, String cdCanalLembrete,
                                      String inPrecisaCuidador, String inJaFezTele) {
        this.idPaciente = idPaciente;
        this.cdHabilidadeDigital = cdHabilidadeDigital;
        this.cdCanalLembrete = cdCanalLembrete;
        this.inPrecisaCuidador = inPrecisaCuidador;
        this.inJaFezTele = inJaFezTele;
    }

    // Getters e Setters
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
}