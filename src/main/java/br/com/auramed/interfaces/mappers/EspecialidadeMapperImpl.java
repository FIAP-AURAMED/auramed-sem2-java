package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.Especialidade;
import br.com.auramed.interfaces.dto.request.EspecialidadeRequestDTO;
import br.com.auramed.interfaces.dto.response.EspecialidadeResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class EspecialidadeMapperImpl implements EspecialidadeMapper {

    @Override
    public Especialidade toDomain(EspecialidadeRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Especialidade especialidade = new Especialidade(dto.getNome());

        if (dto.getId() != null) {
            especialidade.setId(dto.getId());
        } else {
            especialidade.setId(0);
        }

        especialidade.setDescricao(dto.getDescricao());

        return especialidade;
    }

    @Override
    public EspecialidadeResponseDTO toResponseDTO(Especialidade especialidade) {
        if (especialidade == null) {
            return null;
        }

        EspecialidadeResponseDTO response = new EspecialidadeResponseDTO();
        response.setId(especialidade.getId());
        response.setNome(especialidade.getNome());
        response.setDescricao(especialidade.getDescricao());
        response.setAtivo(especialidade.getAtivo());

        return response;
    }

    @Override
    public List<EspecialidadeResponseDTO> toResponseDTOList(List<Especialidade> especialidades) {
        if (especialidades == null) {
            return List.of();
        }
        return especialidades.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}