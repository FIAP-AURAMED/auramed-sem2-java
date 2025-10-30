package br.com.auramed.interfaces.dto.request;

public class AuthMedicoRequestDTO {
    // Atributos
    private Integer id;
    private Integer medicoId;
    private String email;
    private String senha;

    // Construtores
    public AuthMedicoRequestDTO() {}

    public AuthMedicoRequestDTO(Integer id, Integer medicoId, String email, String senha) {
        this.id = id;
        this.medicoId = medicoId;
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}