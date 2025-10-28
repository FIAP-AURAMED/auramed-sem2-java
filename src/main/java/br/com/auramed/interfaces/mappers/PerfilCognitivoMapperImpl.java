package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.PerfilCognitivo;
import br.com.auramed.interfaces.dto.request.PerfilCognitivoRequestDTO;
import br.com.auramed.interfaces.dto.response.PerfilCognitivoResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PerfilCognitivoMapperImpl implements PerfilCognitivoMapper {

    @Override
    public PerfilCognitivo toDomain(PerfilCognitivoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        PerfilCognitivo perfilCognitivo = new PerfilCognitivo(
                dto.getIdPaciente(),
                dto.getInDificuldadeVisao(),
                dto.getInUsaOculos(),
                dto.getInDificuldadeAudicao(),
                dto.getInUsaAparelhoAud(),
                dto.getInDificuldadeCogn()
        );

        return perfilCognitivo;
    }

    @Override
    public PerfilCognitivoResponseDTO toResponseDTO(PerfilCognitivo perfilCognitivo) {
        if (perfilCognitivo == null) {
            return null;
        }

        PerfilCognitivoResponseDTO response = new PerfilCognitivoResponseDTO();
        response.setIdPerfilCognitivo(perfilCognitivo.getIdPerfilCognitivo());
        response.setIdPaciente(perfilCognitivo.getIdPaciente());
        response.setInDificuldadeVisao(perfilCognitivo.getInDificuldadeVisao());
        response.setInUsaOculos(perfilCognitivo.getInUsaOculos());
        response.setInDificuldadeAudicao(perfilCognitivo.getInDificuldadeAudicao());
        response.setInUsaAparelhoAud(perfilCognitivo.getInUsaAparelhoAud());
        response.setInDificuldadeCogn(perfilCognitivo.getInDificuldadeCogn());
        response.setDataCadastro(perfilCognitivo.getDataCadastro());
        response.setDataAtualizacao(perfilCognitivo.getDataAtualizacao());

        return response;
    }

    @Override
    public List<PerfilCognitivoResponseDTO> toResponseDTOList(List<PerfilCognitivo> perfisCognitivos) {
        if (perfisCognitivos == null) {
            return List.of();
        }
        return perfisCognitivos.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}