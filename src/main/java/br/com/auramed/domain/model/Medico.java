package br.com.auramed.domain.model;

import br.com.auramed.domain.exception.ValidacaoDeDominioException;

public class Medico {
    private Integer id;
    private Pessoa pessoa;
    private String crm;
    private String aceitaTeleconsulta;

    public Medico(Pessoa pessoa, String crm) {
        this.pessoa = pessoa;
        this.crm = crm;
        this.aceitaTeleconsulta = "S";
    }

    public void validarPessoa() {
        if (pessoa == null) {
            throw new ValidacaoDeDominioException("Pessoa é obrigatória para o médico.");
        }

        if (!"MEDICO".equals(pessoa.getTipoPessoa())) {
            throw new ValidacaoDeDominioException("Tipo de pessoa deve ser MEDICO.");
        }
    }

    public void validarCrm() {
        if (crm == null || crm.isEmpty() || crm.isBlank()) {
            throw new ValidacaoDeDominioException("CRM está vazio.");
        }

        if (crm.length() > 20) {
            throw new ValidacaoDeDominioException("CRM deve ter menos de 20 caracteres.");
        }

        if (!crm.matches("^[A-Z]{2}-?\\d+$")) {
            throw new ValidacaoDeDominioException("Formato de CRM inválido. Use formato: UF123456");
        }
    }

    public void validarAceitaTeleconsulta() {
        if (aceitaTeleconsulta == null || aceitaTeleconsulta.isEmpty() || aceitaTeleconsulta.isBlank()) {
            throw new ValidacaoDeDominioException("Campo aceita teleconsulta está vazio.");
        }

        if (!aceitaTeleconsulta.matches("^[SN]$")) {
            throw new ValidacaoDeDominioException("Aceita teleconsulta deve ser S (Sim) ou N (Não).");
        }
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        validarPessoa();
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
        validarCrm();
    }

    public String getAceitaTeleconsulta() {
        return aceitaTeleconsulta;
    }

    public void setAceitaTeleconsulta(String aceitaTeleconsulta) {
        this.aceitaTeleconsulta = aceitaTeleconsulta;
        validarAceitaTeleconsulta();
    }
}