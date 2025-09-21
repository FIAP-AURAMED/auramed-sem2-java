package br.com.fiap.auramed.domain.model.entity;

import br.com.fiap.auramed.domain.model.vo.*;

import java.time.LocalDate;

public class Pessoa {
    private NomeCompleto nomeCompleto;
    private Email email;
    private Cpf cpf;
    private DataNascimento dataNascimento;
    private Genero genero;
    private Telefone telefone;

    public Pessoa(NomeCompleto nomeCompleto, Email email, Cpf cpf, DataNascimento dataNascimento, Genero genero, Telefone telefone) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.telefone = telefone;
    }

    public NomeCompleto getNomeCompleto() {
        return nomeCompleto;
    }

    public Email getEmail() {
        return email;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public DataNascimento getDataNascimento() {
        return dataNascimento;
    }

    public Genero getGenero() {
        return genero;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = new NomeCompleto(nomeCompleto);
    }

    public void setEmail(String email) {
        this.email = new Email(email);
    }

    public void setCpf(String cpf) {
        this.cpf = new Cpf(cpf);
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = new DataNascimento(dataNascimento);
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setTelefone(String telefone) {
        this.telefone = new Telefone(telefone);
    }
}
