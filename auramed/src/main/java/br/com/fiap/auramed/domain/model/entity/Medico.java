package br.com.fiap.auramed.domain.model.entity;

import br.com.fiap.auramed.domain.model.vo.*;

public class Medico extends Pessoa {

    public Medico(NomeCompleto nomeCompleto, Email email, Cpf cpf, DataNascimento dataNascimento, Genero genero, Telefone telefone) {
        super(nomeCompleto, email, cpf, dataNascimento, genero, telefone);
    }
}
