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

    public Pessoa(String nomeCompleto, String email, String cpf, LocalDate dataNascimento, Genero genero, String telefone) {
        setNomeCompleto(nomeCompleto);
        setEmail(email);
        setCpf(cpf);
        setEmail(email);
        setDataNascimento(dataNascimento);
        setTelefone(telefone);
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

    public void setTelefone(String telefone) {
        this.telefone = new Telefone(telefone);
    }
}
