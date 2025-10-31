package br.com.auramed.interfaces.dto.response;

import br.com.auramed.domain.model.CategoriaPergunta;
import br.com.auramed.domain.model.Sentimento;

public class ChatResponseDTO {
    // Atributos
    private String resposta;
    private CategoriaPergunta categoria;
    private Sentimento sentimento;
    private String fonteResposta;

    // Construtores
    public ChatResponseDTO() {}

    public ChatResponseDTO(String resposta, CategoriaPergunta categoria,
                           Sentimento sentimento, String fonteResposta) {
        this.resposta = resposta;
        this.categoria = categoria;
        this.sentimento = sentimento;
        this.fonteResposta = fonteResposta;
    }

    // Getters e Setters
    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public CategoriaPergunta getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaPergunta categoria) {
        this.categoria = categoria;
    }

    public Sentimento getSentimento() {
        return sentimento;
    }

    public void setSentimento(Sentimento sentimento) {
        this.sentimento = sentimento;
    }

    public String getFonteResposta() {
        return fonteResposta;
    }

    public void setFonteResposta(String fonteResposta) {
        this.fonteResposta = fonteResposta;
    }
}