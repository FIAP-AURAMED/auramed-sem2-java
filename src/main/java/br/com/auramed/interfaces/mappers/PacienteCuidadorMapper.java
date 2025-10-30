package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.PacienteCuidador;
import br.com.auramed.interfaces.dto.request.PacienteCuidadorRequestDTO;
import br.com.auramed.interfaces.dto.response.PacienteCuidadorResponseDTO;
import java.util.List;

public interface PacienteCuidadorMapper {
    PacienteCuidador toDomain(PacienteCuidadorRequestDTO dto);
    PacienteCuidadorResponseDTO toResponseDTO(PacienteCuidador pacienteCuidador);
    List<PacienteCuidadorResponseDTO> toResponseDTOList(List<PacienteCuidador> pacientesCuidadores);
}