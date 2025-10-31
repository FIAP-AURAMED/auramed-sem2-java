package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.RespostaChat;
import br.com.auramed.interfaces.dto.response.ChatResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ChatMapperImpl implements ChatMapper {

    @Override
    public ChatResponseDTO toResponseDTO(RespostaChat respostaChat) {
        if (respostaChat == null) {
            return null;
        }

        ChatResponseDTO dto = new ChatResponseDTO();
        dto.setResposta(respostaChat.getResposta());
        dto.setCategoria(respostaChat.getCategoria());
        dto.setSentimento(respostaChat.getSentimento());
        dto.setFonteResposta(respostaChat.getFonteResposta());
        dto.setUsuarioId(respostaChat.getUsuarioId());

        return dto;
    }
}