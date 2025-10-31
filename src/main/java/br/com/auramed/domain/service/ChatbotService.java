package br.com.auramed.domain.service;

import br.com.auramed.domain.model.RespostaChat;

public interface ChatbotService {
    RespostaChat processarPergunta(String usuarioId, String perguntaUsuario);
}