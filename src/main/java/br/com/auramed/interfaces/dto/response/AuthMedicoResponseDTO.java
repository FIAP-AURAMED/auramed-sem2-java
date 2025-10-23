package br.com.auramed.interfaces.dto.response;

import java.time.LocalDateTime;

public class AuthMedicoResponseDTO {
    // Atributos
    private Integer id;
    private Integer medicoId;
    private String email;
    private LocalDateTime ultimoLogin;
    private Integer tentativasLogin;
    private String contaAtiva;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAlteracaoSenha;

    // Construtores
    public AuthMedicoResponseDTO() {}

    public AuthMedicoResponseDTO(Integer id, Integer medicoId, String email,
                                 LocalDateTime ultimoLogin, Integer tentativasLogin,
                                 String contaAtiva, LocalDateTime dataCriacao,
                                 LocalDateTime dataAlteracaoSenha) {
        this.id = id;
        this.medicoId = medicoId;
        this.email = email;
        this.ultimoLogin = ultimoLogin;
        this.tentativasLogin = tentativasLogin;
        this.contaAtiva = contaAtiva;
        this.dataCriacao = dataCriacao;
        this.dataAlteracaoSenha = dataAlteracaoSenha;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Integer medicoId) {
        this.medicoId = medicoId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(LocalDateTime ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public Integer getTentativasLogin() {
        return tentativasLogin;
    }

    public void setTentativasLogin(Integer tentativasLogin) {
        this.tentativasLogin = tentativasLogin;
    }

    public String getContaAtiva() {
        return contaAtiva;
    }

    public void setContaAtiva(String contaAtiva) {
        this.contaAtiva = contaAtiva;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAlteracaoSenha() {
        return dataAlteracaoSenha;
    }

    public void setDataAlteracaoSenha(LocalDateTime dataAlteracaoSenha) {
        this.dataAlteracaoSenha = dataAlteracaoSenha;
    }
}