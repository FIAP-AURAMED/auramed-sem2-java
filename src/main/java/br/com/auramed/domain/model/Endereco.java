package br.com.auramed.domain.model;

import br.com.auramed.domain.exception.ValidacaoDeDominioException;

public class Endereco {
    private Integer id;
    private Pessoa pessoa;
    private String tipoEndereco;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private String principal;

    public Endereco(Pessoa pessoa, String logradouro, String numero, String bairro,
                    String cidade, String uf, String cep) {
        this.pessoa = pessoa;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
        this.tipoEndereco = "RESIDENCIAL";
        this.principal = "S";
    }

    public void validarPessoa() {
        if (pessoa == null) {
            throw new ValidacaoDeDominioException("Pessoa é obrigatória para o endereço.");
        }
    }

    public void validarTipoEndereco() {
        if (tipoEndereco == null || tipoEndereco.isEmpty() || tipoEndereco.isBlank()) {
            throw new ValidacaoDeDominioException("Tipo de endereço está vazio.");
        }

        if (!tipoEndereco.matches("^(RESIDENCIAL|COMERCIAL)$")) {
            throw new ValidacaoDeDominioException("Tipo de endereço deve ser RESIDENCIAL ou COMERCIAL.");
        }
    }

    public void validarLogradouro() {
        if (logradouro == null || logradouro.isEmpty() || logradouro.isBlank()) {
            throw new ValidacaoDeDominioException("Logradouro está vazio.");
        }

        if (logradouro.length() > 255) {
            throw new ValidacaoDeDominioException("Logradouro deve ter menos de 255 caracteres.");
        }

        if (logradouro.length() < 3) {
            throw new ValidacaoDeDominioException("Logradouro deve ter pelo menos 3 caracteres.");
        }
    }

    public void validarNumero() {
        if (numero == null || numero.isEmpty() || numero.isBlank()) {
            throw new ValidacaoDeDominioException("Número do endereço está vazio.");
        }

        if (numero.length() > 20) {
            throw new ValidacaoDeDominioException("Número do endereço deve ter menos de 20 caracteres.");
        }
    }

    public void validarBairro() {
        if (bairro == null || bairro.isEmpty() || bairro.isBlank()) {
            throw new ValidacaoDeDominioException("Bairro está vazio.");
        }

        if (bairro.length() > 100) {
            throw new ValidacaoDeDominioException("Bairro deve ter menos de 100 caracteres.");
        }

        if (bairro.length() < 2) {
            throw new ValidacaoDeDominioException("Bairro deve ter pelo menos 2 caracteres.");
        }
    }

    public void validarCidade() {
        if (cidade == null || cidade.isEmpty() || cidade.isBlank()) {
            throw new ValidacaoDeDominioException("Cidade está vazio.");
        }

        if (cidade.length() > 100) {
            throw new ValidacaoDeDominioException("Cidade deve ter menos de 100 caracteres.");
        }

        if (cidade.length() < 2) {
            throw new ValidacaoDeDominioException("Cidade deve ter pelo menos 2 caracteres.");
        }
    }

    public void validarUf() {
        if (uf == null || uf.isEmpty() || uf.isBlank()) {
            throw new ValidacaoDeDominioException("UF está vazio.");
        }

        if (!uf.matches("^[A-Z]{2}$")) {
            throw new ValidacaoDeDominioException("UF deve conter exatamente 2 letras maiúsculas.");
        }
    }

    public void validarCep() {
        if (cep == null || cep.isEmpty() || cep.isBlank()) {
            throw new ValidacaoDeDominioException("CEP está vazio.");
        }

        if (!cep.matches("\\d{8}")) {
            throw new ValidacaoDeDominioException("CEP deve conter exatamente 8 dígitos numéricos.");
        }
    }

    public void validarComplemento() {
        if (complemento != null && complemento.length() > 100) {
            throw new ValidacaoDeDominioException("Complemento deve ter menos de 100 caracteres.");
        }
    }

    public void validarPrincipal() {
        if (principal == null || principal.isEmpty() || principal.isBlank()) {
            throw new ValidacaoDeDominioException("Campo principal está vazio.");
        }

        if (!principal.matches("^[SN]$")) {
            throw new ValidacaoDeDominioException("Campo principal deve ser S (Sim) ou N (Não).");
        }
    }

    public void definirComoPrincipal() {
        this.principal = "S";
    }

    public void definirComoNaoPrincipal() {
        this.principal = "N";
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        validarPessoa();
    }

    public String getTipoEndereco() {
        return tipoEndereco;
    }

    public void setTipoEndereco(String tipoEndereco) {
        this.tipoEndereco = tipoEndereco;
        validarTipoEndereco();
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
        validarLogradouro();
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
        validarNumero();
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
        validarComplemento();
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
        validarBairro();
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
        validarCidade();
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
        validarUf();
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
        validarCep();
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
        validarPrincipal();
    }
}