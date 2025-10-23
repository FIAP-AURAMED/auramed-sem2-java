package br.com.auramed.interfaces.dto.response;

import java.time.LocalDateTime;

public class LoginResponseDTO {
    // Atributos
    private String token;
    private String tipoToken;
    private LocalDateTime dataExpiracao;
    private MedicoResponseDTO medico;

    // Construtores
    public LoginResponseDTO() {}

    public LoginResponseDTO(String token, String tipoToken, LocalDateTime dataExpiracao, MedicoResponseDTO medico) {
        this.token = token;
        this.tipoToken = tipoToken;
        this.dataExpiracao = dataExpiracao;
        this.medico = medico;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipoToken() {
        return tipoToken;
    }

    public void setTipoToken(String tipoToken) {
        this.tipoToken = tipoToken;
    }

    public LocalDateTime getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public MedicoResponseDTO getMedico() {
        return medico;
    }

    public void setMedico(MedicoResponseDTO medico) {
        this.medico = medico;
    }
}