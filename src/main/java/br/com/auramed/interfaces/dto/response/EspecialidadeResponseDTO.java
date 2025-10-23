package br.com.auramed.interfaces.dto.response;

public class EspecialidadeResponseDTO {
    // Atributos
    private Integer id;
    private String nome;
    private String descricao;
    private String ativo;

    // Construtores
    public EspecialidadeResponseDTO() {}

    public EspecialidadeResponseDTO(Integer id, String nome, String descricao, String ativo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }
}