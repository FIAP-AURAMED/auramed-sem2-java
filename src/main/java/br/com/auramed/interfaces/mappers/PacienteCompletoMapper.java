package br.com.auramed.interfaces.mappers;

import br.com.auramed.interfaces.dto.request.PacienteCompletoRequestDTO;
import br.com.auramed.interfaces.dto.response.PacienteCompletoResponseDTO;

public interface PacienteCompletoMapper {
    PacienteCompletoResponseDTO toResponseDTO(PacienteCompletoRequestDTO request);
}