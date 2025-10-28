package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.Cuidador;
import br.com.auramed.interfaces.dto.request.CuidadorRequestDTO;
import br.com.auramed.interfaces.dto.response.CuidadorResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CuidadorMapperImpl implements CuidadorMapper {

    @Override
    public Cuidador toDomain(CuidadorRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Cuidador cuidador = new Cuidador(dto.getIdPessoa(), dto.getParentesco(), dto.getTempoCuidado());

        return cuidador;
    }

    @Override
    public CuidadorResponseDTO toResponseDTO(Cuidador cuidador) {
        if (cuidador == null) {
            return null;
        }

        CuidadorResponseDTO response = new CuidadorResponseDTO();
        response.setIdPessoa(cuidador.getIdPessoa());
        response.setParentesco(cuidador.getParentesco());
        response.setTempoCuidado(cuidador.getTempoCuidado());
        response.setDataCadastro(cuidador.getDataCadastro());
        response.setAtivo(cuidador.getAtivo());

        return response;
    }

    @Override
    public List<CuidadorResponseDTO> toResponseDTOList(List<Cuidador> cuidadores) {
        if (cuidadores == null) {
            return List.of();
        }
        return cuidadores.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}