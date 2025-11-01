package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.BaseConhecimento;
import br.com.auramed.interfaces.dto.request.BaseConhecimentoRequestDTO;
import br.com.auramed.interfaces.dto.response.BaseConhecimentoResponseDTO;

import java.util.List;

public interface BaseConhecimentoMapper {
    BaseConhecimento toDomain(BaseConhecimentoRequestDTO dto);
    BaseConhecimentoResponseDTO toResponseDTO(BaseConhecimento conhecimento);
    List<BaseConhecimentoResponseDTO> toResponseDTOList(List<BaseConhecimento> conhecimentos);
}