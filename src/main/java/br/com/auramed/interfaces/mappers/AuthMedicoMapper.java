package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.AuthMedico;
import br.com.auramed.interfaces.dto.request.AuthMedicoRequestDTO;
import br.com.auramed.interfaces.dto.response.AuthMedicoResponseDTO;
import java.util.List;

public interface AuthMedicoMapper {
    AuthMedico toDomain(AuthMedicoRequestDTO dto);
    AuthMedicoResponseDTO toResponseDTO(AuthMedico authMedico);
    List<AuthMedicoResponseDTO> toResponseDTOList(List<AuthMedico> authMedicos);
}