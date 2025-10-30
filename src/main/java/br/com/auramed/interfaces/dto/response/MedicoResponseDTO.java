package br.com.auramed.interfaces.dto.response;

import java.time.LocalDateTime;

public class MedicoResponseDTO {
    // Atributos
    private Integer id;
    private PessoaResponseDTO pessoa;
    private String crm;
    private String aceitaTeleconsulta;
    private LocalDateTime dataCadastro;

    // Construtores
    public MedicoResponseDTO() {}

    public MedicoResponseDTO(Integer id, PessoaResponseDTO pessoa, String crm,
                             String aceitaTeleconsulta, LocalDateTime dataCadastro) {
        this.id = id;
        this.pessoa = pessoa;
        this.crm = crm;
        this.aceitaTeleconsulta = aceitaTeleconsulta;
        this.dataCadastro = dataCadastro;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PessoaResponseDTO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaResponseDTO pessoa) {
        this.pessoa = pessoa;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getAceitaTeleconsulta() {
        return aceitaTeleconsulta;
    }

    public void setAceitaTeleconsulta(String aceitaTeleconsulta) {
        this.aceitaTeleconsulta = aceitaTeleconsulta;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}