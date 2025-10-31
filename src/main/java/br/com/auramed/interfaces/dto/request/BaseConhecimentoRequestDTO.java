package br.com.auramed.interfaces.dto.request;

public class BaseConhecimentoRequestDTO {
    // Atributos
    private String pergunta;
    private String resposta;
    private String categoria;
    private String palavrasChave;
    private Double confianca;

    // Construtores
    public BaseConhecimentoRequestDTO() {}

    public BaseConhecimentoRequestDTO(String pergunta, String resposta, String categoria,
                                      String palavrasChave, Double confianca) {
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.categoria = categoria;
        this.palavrasChave = palavrasChave;
        this.confianca = confianca;
    }

    // Getters e Setters
    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPalavrasChave() {
        return palavrasChave;
    }

    public void setPalavrasChave(String palavrasChave) {
        this.palavrasChave = palavrasChave;
    }

    public Double getConfianca() {
        return confianca;
    }

    public void setConfianca(Double confianca) {
        this.confianca = confianca;
    }
}