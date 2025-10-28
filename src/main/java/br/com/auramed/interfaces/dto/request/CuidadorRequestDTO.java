package br.com.auramed.interfaces.dto.request;

public class CuidadorRequestDTO {
    private Integer idPessoa;
    private String parentesco;
    private String tempoCuidado;

    // Construtores
    public CuidadorRequestDTO() {}

    public CuidadorRequestDTO(Integer idPessoa, String parentesco, String tempoCuidado) {
        this.idPessoa = idPessoa;
        this.parentesco = parentesco;
        this.tempoCuidado = tempoCuidado;
    }

    // Getters e Setters
    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getTempoCuidado() {
        return tempoCuidado;
    }

    public void setTempoCuidado(String tempoCuidado) {
        this.tempoCuidado = tempoCuidado;
    }
}