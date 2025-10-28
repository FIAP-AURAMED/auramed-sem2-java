package br.com.auramed.interfaces.dto.request;

public class PerfilCognitivoRequestDTO {
    private Integer idPaciente;
    private String inDificuldadeVisao;
    private String inUsaOculos;
    private String inDificuldadeAudicao;
    private String inUsaAparelhoAud;
    private String inDificuldadeCogn;

    // Construtores
    public PerfilCognitivoRequestDTO() {}

    public PerfilCognitivoRequestDTO(Integer idPaciente, String inDificuldadeVisao, String inUsaOculos,
                                     String inDificuldadeAudicao, String inUsaAparelhoAud, String inDificuldadeCogn) {
        this.idPaciente = idPaciente;
        this.inDificuldadeVisao = inDificuldadeVisao;
        this.inUsaOculos = inUsaOculos;
        this.inDificuldadeAudicao = inDificuldadeAudicao;
        this.inUsaAparelhoAud = inUsaAparelhoAud;
        this.inDificuldadeCogn = inDificuldadeCogn;
    }

    // Getters e Setters
    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getInDificuldadeVisao() {
        return inDificuldadeVisao;
    }

    public void setInDificuldadeVisao(String inDificuldadeVisao) {
        this.inDificuldadeVisao = inDificuldadeVisao;
    }

    public String getInUsaOculos() {
        return inUsaOculos;
    }

    public void setInUsaOculos(String inUsaOculos) {
        this.inUsaOculos = inUsaOculos;
    }

    public String getInDificuldadeAudicao() {
        return inDificuldadeAudicao;
    }

    public void setInDificuldadeAudicao(String inDificuldadeAudicao) {
        this.inDificuldadeAudicao = inDificuldadeAudicao;
    }

    public String getInUsaAparelhoAud() {
        return inUsaAparelhoAud;
    }

    public void setInUsaAparelhoAud(String inUsaAparelhoAud) {
        this.inUsaAparelhoAud = inUsaAparelhoAud;
    }

    public String getInDificuldadeCogn() {
        return inDificuldadeCogn;
    }

    public void setInDificuldadeCogn(String inDificuldadeCogn) {
        this.inDificuldadeCogn = inDificuldadeCogn;
    }
}