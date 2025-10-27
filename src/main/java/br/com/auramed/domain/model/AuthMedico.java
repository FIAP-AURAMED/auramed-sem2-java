package br.com.auramed.domain.model;

import br.com.auramed.domain.exception.ValidacaoDeDominioException;
import java.time.LocalDateTime;

public class AuthMedico {
    private Integer id;
    private Medico medico;
    private String email;
    private String senhaHash;
    private LocalDateTime ultimoLogin;
    private Integer tentativasLogin;
    private String contaAtiva;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAlteracaoSenha;

    public AuthMedico(Medico medico, String email, String senhaHash) {
        this.medico = medico;
        this.email = email;
        this.senhaHash = senhaHash;
        this.tentativasLogin = 0;
        this.contaAtiva = "S";
        this.dataCriacao = LocalDateTime.now();
        this.dataAlteracaoSenha = LocalDateTime.now();
    }

    public void validarMedico() {
        if (medico == null) {
            throw new ValidacaoDeDominioException("Médico é obrigatório para autenticação.");
        }
    }

    public void validarEmail() {
        if (email == null || email.isEmpty() || email.isBlank()) {
            throw new ValidacaoDeDominioException("Email de autenticação está vazio.");
        }

        final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.matches(regex)) {
            throw new ValidacaoDeDominioException("Formato de email de autenticação inválido.");
        }

        if (email.length() > 255) {
            throw new ValidacaoDeDominioException("Email de autenticação deve ter menos de 255 caracteres.");
        }
    }

    public void validarSenhaHash() {
        if (senhaHash == null || senhaHash.isEmpty() || senhaHash.isBlank()) {
            throw new ValidacaoDeDominioException("Hash da senha está vazio.");
        }

        if (senhaHash.length() < 60) {
            throw new ValidacaoDeDominioException("Hash da senha parece estar em formato inválido.");
        }
    }

    public void validarTentativasLogin() {
        if (tentativasLogin == null || tentativasLogin < 0) {
            throw new ValidacaoDeDominioException("Número de tentativas de login inválido.");
        }

        if (tentativasLogin > 10) {
            bloquearConta();
        }
    }

    public void validarContaAtiva() {
        if (contaAtiva == null || contaAtiva.isEmpty() || contaAtiva.isBlank()) {
            throw new ValidacaoDeDominioException("Status da conta está vazio.");
        }

        if (!contaAtiva.matches("^[SN]$")) {
            throw new ValidacaoDeDominioException("Status da conta deve ser S (Ativa) ou N (Inativa).");
        }
    }

    public void incrementarTentativaLogin() {
        this.tentativasLogin++;
        validarTentativasLogin();
    }

    public void resetarTentativasLogin() {
        this.tentativasLogin = 0;
    }

    public void bloquearConta() {
        this.contaAtiva = "N";
    }

    public void ativarConta() {
        this.contaAtiva = "S";
        resetarTentativasLogin();
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
        validarMedico();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        validarEmail();
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
        this.dataAlteracaoSenha = LocalDateTime.now();
        validarSenhaHash();
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
        validarTentativasLogin();
    }

    public String getContaAtiva() {
        return contaAtiva;
    }

    public void setContaAtiva(String contaAtiva) {
        this.contaAtiva = contaAtiva;
        validarContaAtiva();
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