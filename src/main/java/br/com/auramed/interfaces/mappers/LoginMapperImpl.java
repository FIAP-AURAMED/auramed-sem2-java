package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.Medico;
import br.com.auramed.interfaces.dto.response.LoginResponseDTO;
import br.com.auramed.interfaces.dto.response.MedicoResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDateTime;

@ApplicationScoped
public class LoginMapperImpl implements LoginMapper {

    @Inject
    MedicoMapper medicoMapper;

    @Override
    public LoginResponseDTO toResponseDTO(String token, String tipoToken, LocalDateTime dataExpiracao, Medico medico) {
        if (token == null || medico == null) {
            return null;
        }

        MedicoResponseDTO medicoResponse = medicoMapper.toResponseDTO(medico);

        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(token);
        response.setTipoToken(tipoToken);
        response.setDataExpiracao(dataExpiracao);
        response.setMedico(medicoResponse);

        return response;
    }
}