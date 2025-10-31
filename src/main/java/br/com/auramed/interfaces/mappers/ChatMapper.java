package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.RespostaChat;
import br.com.auramed.interfaces.dto.response.ChatResponseDTO;

public interface ChatMapper {
    ChatResponseDTO toResponseDTO(RespostaChat respostaChat);
}