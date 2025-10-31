package br.com.auramed.interfaces.controllers;

import br.com.auramed.interfaces.dto.request.ChatRequestDTO;
import br.com.auramed.interfaces.dto.response.ChatResponseDTO;

public interface ChatbotController {
    ChatResponseDTO processarMensagem(ChatRequestDTO chatRequest);
}