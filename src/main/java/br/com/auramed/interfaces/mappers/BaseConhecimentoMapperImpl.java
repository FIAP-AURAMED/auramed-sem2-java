package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.BaseConhecimento;
import br.com.auramed.interfaces.dto.request.BaseConhecimentoRequestDTO;
import br.com.auramed.interfaces.dto.response.BaseConhecimentoResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BaseConhecimentoMapperImpl implements BaseConhecimentoMapper {

    @Override
    public BaseConhecimento toDomain(BaseConhecimentoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        BaseConhecimento conhecimento = new BaseConhecimento();
        conhecimento.setPergunta(dto.getPergunta());
        conhecimento.setResposta(dto.getResposta());
        conhecimento.setCategoria(dto.getCategoria());
        conhecimento.setPalavrasChave(dto.getPalavrasChave());
        conhecimento.setConfianca(dto.getConfianca());

        return conhecimento;
    }

    @Override
    public BaseConhecimentoResponseDTO toResponseDTO(BaseConhecimento conhecimento) {
        if (conhecimento == null) {
            return null;
        }

        BaseConhecimentoResponseDTO response = new BaseConhecimentoResponseDTO();
        response.setId(conhecimento.getId());
        response.setPergunta(conhecimento.getPergunta());
        response.setResposta(conhecimento.getResposta());
        response.setCategoria(conhecimento.getCategoria());
        response.setPalavrasChave(conhecimento.getPalavrasChave());
        response.setConfianca(conhecimento.getConfianca());
        return response;
    }

    @Override
    public List<BaseConhecimentoResponseDTO> toResponseDTOList(List<BaseConhecimento> conhecimentos) {
        if (conhecimentos == null) {
            return List.of();
        }
        return conhecimentos.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}