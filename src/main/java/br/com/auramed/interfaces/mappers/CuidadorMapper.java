package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.Cuidador;
import br.com.auramed.interfaces.dto.request.CuidadorRequestDTO;
import br.com.auramed.interfaces.dto.response.CuidadorResponseDTO;
import java.util.List;

public interface CuidadorMapper {
    Cuidador toDomain(CuidadorRequestDTO dto);
    CuidadorResponseDTO toResponseDTO(Cuidador cuidador);
    List<CuidadorResponseDTO> toResponseDTOList(List<Cuidador> cuidadores);
}