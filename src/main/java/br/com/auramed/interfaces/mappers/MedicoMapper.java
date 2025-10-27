package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.Medico;
import br.com.auramed.interfaces.dto.request.MedicoRequestDTO;
import br.com.auramed.interfaces.dto.response.MedicoResponseDTO;
import java.util.List;

public interface MedicoMapper {
    Medico toDomain(MedicoRequestDTO dto);
    MedicoResponseDTO toResponseDTO(Medico medico);
    List<MedicoResponseDTO> toResponseDTOList(List<Medico> medicos);
}