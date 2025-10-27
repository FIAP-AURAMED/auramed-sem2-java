package br.com.auramed.interfaces.mappers;

import br.com.auramed.interfaces.dto.response.LoginResponseDTO;
import br.com.auramed.domain.model.Medico;

public interface LoginMapper {
    LoginResponseDTO toResponseDTO(String token, String tipoToken, java.time.LocalDateTime dataExpiracao, Medico medico);
}