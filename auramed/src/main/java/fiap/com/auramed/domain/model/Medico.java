package fiap.com.auramed.domain.model;

import fiap.com.auramed.domain.exception.DomainValidationException;
import java.time.LocalDate;

public class Medico extends Pessoa {

    private String crm;
    private String especialidade;
    private String senha;

    public Medico(String nome, String email, String telefone, String cpf, LocalDate dataNascimento, Genero genero, String crm, String especialidade, String senha) {
        super(nome, email, telefone, cpf, dataNascimento, genero);
        this.setCrm(crm);
        this.setEspecialidade(especialidade);
        this.setSenha(senha);
    }

    public void isCrmValid() {
        if (crm == null || crm.trim().isEmpty()) {
            throw new DomainValidationException("CRM não pode ser vazio ou nulo.");
        }
        if (crm.length() > 20) {
            throw new DomainValidationException("CRM deve ter no máximo 20 caracteres.");
        }
    }

    public void isEspecialidadeValid() {
        if (especialidade == null || especialidade.trim().isEmpty()) {
            throw new DomainValidationException("Especialidade não pode ser vazia ou nula.");
        }
        if (especialidade.length() > 100) {
            throw new DomainValidationException("Especialidade deve ter no máximo 100 caracteres.");
        }
    }

    public void isSenhaValid() {
        if (senha == null || senha.isEmpty()) {
            throw new DomainValidationException("Senha não pode ser vazia ou nula.");
        }
        if (senha.length() < 8) {
            throw new DomainValidationException("Senha deve ter no mínimo 8 caracteres.");
        }
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
        isCrmValid();
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
        isEspecialidadeValid();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
        isSenhaValid();
    }
}