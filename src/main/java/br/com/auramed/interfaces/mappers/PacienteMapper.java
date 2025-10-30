package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.Paciente;
import br.com.auramed.interfaces.dto.request.PacienteRequestDTO;
import br.com.auramed.interfaces.dto.response.PacienteResponseDTO;
import java.util.List;

public interface PacienteMapper {
    Paciente toDomain(PacienteRequestDTO dto);
    PacienteResponseDTO toResponseDTO(Paciente paciente);
    List<PacienteResponseDTO> toResponseDTOList(List<Paciente> pacientes);
}