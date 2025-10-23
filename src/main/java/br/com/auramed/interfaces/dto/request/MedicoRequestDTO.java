package br.com.auramed.interfaces.dto.request;

public class MedicoRequestDTO {
    // Atributos
    private Integer id;
    private PessoaRequestDTO pessoa;
    private String crm;
    private String aceitaTeleconsulta;

    // Construtores
    public MedicoRequestDTO() {}

    public MedicoRequestDTO(Integer id, PessoaRequestDTO pessoa, String crm, String aceitaTeleconsulta) {
        this.id = id;
        this.pessoa = pessoa;
        this.crm = crm;
        this.aceitaTeleconsulta = aceitaTeleconsulta;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PessoaRequestDTO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaRequestDTO pessoa) {
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
}