package br.com.fiap.auramed.domain.model.entity;

import br.com.fiap.auramed.domain.model.vo.*;

public class Medico extends Pessoa {

    private RegistroProfissional crm;
    private String senhaHash;

    public Medico(
            NomeCompleto nomeCompleto,
            Email email,
            Cpf cpf,
            DataNascimento dataNascimento,
            Genero genero,
            Telefone telefone,
            RegistroProfissional crm,
            String senhaHash) {

        super(nomeCompleto, email, cpf, dataNascimento, genero, telefone);

        this.crm = crm;
        this.senhaHash = senhaHash;
    }

    public RegistroProfissional getCrm() {
        return crm;
    }

    public String getSenhaHash() {
        return senhaHash;
    }
}