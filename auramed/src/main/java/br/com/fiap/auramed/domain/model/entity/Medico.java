package br.com.fiap.auramed.domain.model.entity;

import br.com.fiap.auramed.domain.model.vo.*;
import java.time.LocalDate;

public class Medico extends Pessoa {

    private final RegistroProfissional crm;
    private final String senhaHash;

    public Medico(
            String nomeCompleto,
            String email,
            String cpf,
            LocalDate dataNascimento,
            Genero genero,
            String telefone,
            Endereco endereco,
            String crmNumero,
            UF crmUf,
            String senha) {

        // Atributos em Comum
        super(nomeCompleto, email, cpf, dataNascimento, genero, telefone, endereco);

        this.crm = new RegistroProfissional(crmNumero, crmUf);
        this.senhaHash = senha;
    }

    // Getters
    public RegistroProfissional getCrm() {
        return crm;
    }

    public String getSenhaHash() {
        return senhaHash;
    }
}