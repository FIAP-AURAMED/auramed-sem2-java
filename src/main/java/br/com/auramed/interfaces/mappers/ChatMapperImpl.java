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

        ChatResponseDTO response = new ChatResponseDTO();
        response.setResposta(respostaChat.getResposta());
        response.setCategoria(respostaChat.getCategoria());
        response.setSentimento(respostaChat.getSentimento());
        response.setFonteResposta(respostaChat.getFonteResposta());

        return response;
    }
}