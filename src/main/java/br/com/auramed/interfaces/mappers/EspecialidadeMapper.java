package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.Especialidade;
import br.com.auramed.interfaces.dto.request.EspecialidadeRequestDTO;
import br.com.auramed.interfaces.dto.response.EspecialidadeResponseDTO;
import java.util.List;

public interface EspecialidadeMapper {
    Especialidade toDomain(EspecialidadeRequestDTO dto);
    EspecialidadeResponseDTO toResponseDTO(Especialidade especialidade);
    List<EspecialidadeResponseDTO> toResponseDTOList(List<Especialidade> especialidades);
}