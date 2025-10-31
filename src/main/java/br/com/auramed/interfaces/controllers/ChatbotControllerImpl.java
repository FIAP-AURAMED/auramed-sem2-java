package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.model.RespostaChat; // Import adicionado
import br.com.auramed.domain.service.ChatbotService;
import br.com.auramed.interfaces.dto.request.ChatRequestDTO;
import br.com.auramed.interfaces.dto.response.ChatResponseDTO;
import br.com.auramed.interfaces.mappers.ChatMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ChatbotControllerImpl implements ChatbotController {
    private final ChatbotService chatbotService;
    private final ChatMapper chatMapper;

    @Inject
    Logger logger;

    @Inject
    public ChatbotControllerImpl(ChatbotService chatbotService, ChatMapper chatMapper) {
        this.chatbotService = chatbotService;
        this.chatMapper = chatMapper;
    }

    @Override
    public ChatResponseDTO processarMensagem(ChatRequestDTO chatRequest) {
        try {
            logger.info("Processando mensagem do usuário: " + chatRequest.getUsuarioId());

            RespostaChat respostaChat = chatbotService.processarPergunta(
                    chatRequest.getUsuarioId(),
                    chatRequest.getMensagem()
            );

            ChatResponseDTO responseDTO = chatMapper.toResponseDTO(respostaChat);
            logger.info("Resposta gerada com sucesso para usuário: " + chatRequest.getUsuarioId());

            return responseDTO;

        } catch (Exception e) {
            logger.error("Erro ao processar mensagem: " + e.getMessage(), e);
            throw new RuntimeException("Falha ao processar mensagem: " + e.getMessage());
        }
    }
}