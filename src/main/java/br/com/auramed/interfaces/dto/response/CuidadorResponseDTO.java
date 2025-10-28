package br.com.auramed.interfaces.dto.response;

import java.time.LocalDateTime;

public class CuidadorResponseDTO {
    private Integer idPessoa;
    private String parentesco;
    private String tempoCuidado;
    private LocalDateTime dataCadastro;
    private String ativo;

    // Construtores
    public CuidadorResponseDTO() {}

    public CuidadorResponseDTO(Integer idPessoa, String parentesco, String tempoCuidado,
                               LocalDateTime dataCadastro, String ativo) {
        this.idPessoa = idPessoa;
        this.parentesco = parentesco;
        this.tempoCuidado = tempoCuidado;
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