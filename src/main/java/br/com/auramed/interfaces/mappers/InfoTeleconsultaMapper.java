package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.InfoTeleconsulta;
import br.com.auramed.interfaces.dto.request.InfoTeleconsultaRequestDTO;
import br.com.auramed.interfaces.dto.response.InfoTeleconsultaResponseDTO;
import java.util.List;

public interface InfoTeleconsultaMapper {
    InfoTeleconsulta toDomain(InfoTeleconsultaRequestDTO dto);
    InfoTeleconsultaResponseDTO toResponseDTO(InfoTeleconsulta infoTeleconsulta);
    List<InfoTeleconsultaResponseDTO> toResponseDTOList(List<InfoTeleconsulta> infoTeleconsultas);
}