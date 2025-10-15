package fiap.com.auramed.domain.model;

import fiap.com.auramed.domain.exception.DomainValidationException;
import java.time.LocalDate;
import java.util.List;

public class Pessoa {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private LocalDate dataNascimento;
    private Genero genero;
    private StatusUsuario status;
    private List<Endereco> enderecos;

    public Pessoa(String nome, String email, String telefone, String cpf, LocalDate dataNascimento, Genero genero) {
        this.setNome(nome);
        this.setEmail(email);
        this.setTelefone(telefone);
        this.setCpf(cpf);
        this.setDataNascimento(dataNascimento);
        this.setGenero(genero);
        this.status = StatusUsuario.ATIVO;
    }

    public void isNomeValid() {
        if (nome == null || nome.trim().isEmpty()) {
            throw new DomainValidationException("Nome não pode ser vazio ou nulo.");
        }
        if (nome.length() > 100) {
            throw new DomainValidationException("Nome deve ter menos de 100 caracteres.");
        }
        if (nome.trim().split("\\s+").length < 2) {
            throw new DomainValidationException("Nome deve conter pelo menos nome e sobrenome.");
        }
    }

    public void isEmailValid() {
        if (email == null || email.trim().isEmpty()) {
            throw new DomainValidationException("Email não pode ser vazio ou nulo.");
        }
        final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.matches(regex)) {
            throw new DomainValidationException("Formato de email inválido.");
        }
        if (email.length() > 255) {
            throw new DomainValidationException("Email deve ter menos de 255 caracteres.");
        }
    }

    public void isStatusValid() {
        if (status == null) {
            throw new DomainValidationException("Status não pode ser nulo.");
        }
    }

    public void isTelefoneValid() {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new DomainValidationException("Telefone não pode ser vazio ou nulo.");
        }
        String digitsOnly = telefone.replaceAll("\\D", "");
        if (digitsOnly.length() < 10 || digitsOnly.length() > 11) {
            throw new DomainValidationException("Telefone inválido. Deve conter DDD + 8 ou 9 dígitos.");
        }
    }

    public void isCpfValid() {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new DomainValidationException("CPF não pode ser vazio ou nulo.");
        }
        String digitsOnly = cpf.replaceAll("\\D", "");
        if (digitsOnly.length() != 11) {
            throw new DomainValidationException("CPF deve conter exatamente 11 dígitos.");
        }
        if (digitsOnly.matches("(\\d)\\1{10}")) {
            throw new DomainValidationException("CPF com todos os dígitos iguais é inválido.");
        }
    }

    public void isDataNascimentoValid() {
        if (dataNascimento == null) {
            throw new DomainValidationException("Data de nascimento não pode ser nula.");
        }
        if (dataNascimento.isAfter(LocalDate.now())) {
            throw new DomainValidationException("Data de nascimento não pode ser uma data futura.");
        }
    }

    public void isGeneroValid() {
        if (genero == null) {
            throw new DomainValidationException("Gênero não pode ser nulo.");
        }
    }

    public void inativar() {
        this.status = StatusUsuario.INATIVO;
    }

    public void ativar() {
        this.status = StatusUsuario.ATIVO;
    }

    public boolean isAtivo() {
        return this.status == StatusUsuario.ATIVO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        isNomeValid();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        isEmailValid();
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
        isTelefoneValid();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
        isCpfValid();
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        isDataNascimentoValid();
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
        isGeneroValid();
    }

    public StatusUsuario getStatus() {
        return status;
    }

    public void setStatus(StatusUsuario status) {
        this.status = status;
        isStatusValid();
    }
}