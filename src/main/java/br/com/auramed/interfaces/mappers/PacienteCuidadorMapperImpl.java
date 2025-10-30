package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.PacienteCuidador;
import br.com.auramed.interfaces.dto.request.PacienteCuidadorRequestDTO;
import br.com.auramed.interfaces.dto.response.PacienteCuidadorResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PacienteCuidadorMapperImpl implements PacienteCuidadorMapper {

    @Override
    public PacienteCuidador toDomain(PacienteCuidadorRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        PacienteCuidador pacienteCuidador = new PacienteCuidador(
                dto.getIdPaciente(),
                dto.getIdCuidador(),
                dto.getTipoRelacionamento()
        );

        return pacienteCuidador;
    }

    @Override
    public PacienteCuidadorResponseDTO toResponseDTO(PacienteCuidador pacienteCuidador) {
        if (pacienteCuidador == null) {
            return null;
        }

        PacienteCuidadorResponseDTO response = new PacienteCuidadorResponseDTO();
        response.setIdPaciente(pacienteCuidador.getIdPaciente());
        response.setIdCuidador(pacienteCuidador.getIdCuidador());
        response.setTipoRelacionamento(pacienteCuidador.getTipoRelacionamento());
        response.setDataAssociacao(pacienteCuidador.getDataAssociacao());
        response.setAtivo(pacienteCuidador.getAtivo());

        return response;
    }

    @Override
    public List<PacienteCuidadorResponseDTO> toResponseDTOList(List<PacienteCuidador> pacientesCuidadores) {
        if (pacientesCuidadores == null) {
            return List.of();
        }
        return pacientesCuidadores.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}