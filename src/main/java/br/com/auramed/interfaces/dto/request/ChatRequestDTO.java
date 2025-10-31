package br.com.auramed.interfaces.dto.request;

public class ChatRequestDTO {
    // Atributos
    private String usuarioId;
    private String mensagem;

    // Construtores
    public ChatRequestDTO() {}

    public ChatRequestDTO(String usuarioId, String mensagem) {
        this.usuarioId = usuarioId;
        this.mensagem = mensagem;
    }

    // Getters e Setters
    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}