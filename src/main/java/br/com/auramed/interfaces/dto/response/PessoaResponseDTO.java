package br.com.auramed.interfaces.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PessoaResponseDTO {
    // Atributos
    private Integer id;
    private String nome;
    private String email;
    private String cpf;
    private LocalDate dataNascimento;
    private String genero;
    private String telefone;
    private String tipoPessoa;
    private LocalDateTime dataCadastro;
    private String ativo;

    // Construtores
    public PessoaResponseDTO() {}

    public PessoaResponseDTO(Integer id, String nome, String email, String cpf,
                             LocalDate dataNascimento, String genero, String telefone,
                             String tipoPessoa, LocalDateTime dataCadastro, String ativo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.telefone = telefone;
        this.tipoPessoa = tipoPessoa;
        this.dataCadastro = dataCadastro;
        this.ativo = ativo;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
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