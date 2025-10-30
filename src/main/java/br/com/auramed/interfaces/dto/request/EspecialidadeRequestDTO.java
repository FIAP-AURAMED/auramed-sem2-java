package br.com.auramed.interfaces.dto.request;

public class EspecialidadeRequestDTO {
    // Atributos
    private Integer id;
    private String nome;
    private String descricao;

    // Construtores
    public EspecialidadeRequestDTO() {}

    public EspecialidadeRequestDTO(Integer id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    // Getters e Setters
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
}