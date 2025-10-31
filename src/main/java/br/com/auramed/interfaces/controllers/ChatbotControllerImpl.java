package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.model.RespostaChat;
import br.com.auramed.domain.service.ChatbotService;
import br.com.auramed.interfaces.dto.request.ChatRequestDTO;
import br.com.auramed.interfaces.dto.response.ChatResponseDTO;
import br.com.auramed.interfaces.mappers.ChatMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.UUID;

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
            String usuarioId = chatRequest.getUsuarioId();

            if (usuarioId == null || usuarioId.trim().isEmpty()) {
                usuarioId = "anon_" + UUID.randomUUID().toString().substring(0, 8);
                logger.info("ID automático gerado: " + usuarioId);
            }

            logger.info("Processando mensagem do usuário: " + usuarioId);

            RespostaChat respostaChat = chatbotService.processarPergunta(
                    usuarioId,
                    chatRequest.getMensagem()
            );

            ChatResponseDTO responseDTO = chatMapper.toResponseDTO(respostaChat);
            logger.info("Resposta gerada com sucesso para usuário: " + usuarioId);

            return responseDTO;

        } catch (Exception e) {
            logger.error("Erro ao processar mensagem: " + e.getMessage(), e);
            throw new RuntimeException("Falha ao processar mensagem: " + e.getMessage());
        }
    }
}