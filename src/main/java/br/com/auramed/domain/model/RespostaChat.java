package br.com.auramed.domain.model;

public class RespostaChat {
    private String resposta;
    private CategoriaPergunta categoria;
    private Sentimento sentimento;
    private String fonteResposta;

    public RespostaChat(String resposta, CategoriaPergunta categoria,
                        Sentimento sentimento, String fonteResposta) {
        this.resposta = resposta;
        this.categoria = categoria;
        this.sentimento = sentimento;
        this.fonteResposta = fonteResposta;
    }

    // getters
    public String getResposta() { return resposta; }
    public CategoriaPergunta getCategoria() { return categoria; }
    public Sentimento getSentimento() { return sentimento; }
    public String getFonteResposta() { return fonteResposta; }

    // setters (se necessário)
    public void setResposta(String resposta) { this.resposta = resposta; }
    public void setCategoria(CategoriaPergunta categoria) { this.categoria = categoria; }
    public void setSentimento(Sentimento sentimento) { this.sentimento = sentimento; }
    public void setFonteResposta(String fonteResposta) { this.fonteResposta = fonteResposta; }
}
