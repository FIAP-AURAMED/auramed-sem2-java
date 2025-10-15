package fiap.com.auramed.domain.model;

import fiap.com.auramed.domain.exception.DomainValidationException;

public class Endereco {
    private int id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private Pessoa pessoa;

    public Endereco(String logradouro, String numero, String complemento, String bairro, String cidade, String uf, String cep, Pessoa pessoa) {
        this.setLogradouro(logradouro);
        this.setNumero(numero);
        this.setComplemento(complemento);
        this.setBairro(bairro);
        this.setCidade(cidade);
        this.setUf(uf);
        this.setCep(cep);
        this.setPessoa(pessoa);
    }

    public void isLogradouroValid() {
        if (logradouro == null || logradouro.trim().isEmpty()) {
            throw new DomainValidationException("Logradouro não pode ser vazio.");
        }
    }

    public void isNumeroValid() {
        if (numero == null || numero.trim().isEmpty()) {
            throw new DomainValidationException("Número do endereço não pode ser vazio.");
        }
    }

    public void isBairroValid() {
        if (bairro == null || bairro.trim().isEmpty()) {
            throw new DomainValidationException("Bairro não pode ser vazio.");
        }
    }

    public void isCidadeValid() {
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new DomainValidationException("Cidade não pode ser vazia.");
        }
    }

    public void isUfValid() {
        if (uf == null || uf.trim().length() != 2) {
            throw new DomainValidationException("UF deve ter exatamente 2 caracteres.");
        }
    }

    public void isCepValid() {
        if (cep == null || cep.replaceAll("\\D", "").length() != 8) {
            throw new DomainValidationException("CEP deve conter 8 dígitos.");
        }
    }

    public void isPessoaValid() {
        if (pessoa == null) {
            throw new DomainValidationException("Endereço deve estar associado a uma pessoa.");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}