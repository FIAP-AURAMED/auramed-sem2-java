package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.InfoTeleconsulta;
import br.com.auramed.interfaces.dto.request.InfoTeleconsultaRequestDTO;
import br.com.auramed.interfaces.dto.response.InfoTeleconsultaResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class InfoTeleconsultaMapperImpl implements InfoTeleconsultaMapper {

    @Override
    public InfoTeleconsulta toDomain(InfoTeleconsultaRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        InfoTeleconsulta infoTeleconsulta = new InfoTeleconsulta(
                dto.getIdPaciente(),
                dto.getCdHabilidadeDigital(),
                dto.getCdCanalLembrete(),
                dto.getInPrecisaCuidador(),
                dto.getInJaFezTele()
        );

        return infoTeleconsulta;
    }

    @Override
    public InfoTeleconsultaResponseDTO toResponseDTO(InfoTeleconsulta infoTeleconsulta) {
        if (infoTeleconsulta == null) {
            return null;
        }

        InfoTeleconsultaResponseDTO response = new InfoTeleconsultaResponseDTO();
        response.setIdInfoTeleconsulta(infoTeleconsulta.getIdInfoTeleconsulta());
        response.setIdPaciente(infoTeleconsulta.getIdPaciente());
        response.setCdHabilidadeDigital(infoTeleconsulta.getCdHabilidadeDigital());
        response.setCdCanalLembrete(infoTeleconsulta.getCdCanalLembrete());
        response.setInPrecisaCuidador(infoTeleconsulta.getInPrecisaCuidador());
        response.setInJaFezTele(infoTeleconsulta.getInJaFezTele());
        response.setDataCadastro(infoTeleconsulta.getDataCadastro());
        response.setDataAtualizacao(infoTeleconsulta.getDataAtualizacao());

        return response;
    }

    @Override
    public List<InfoTeleconsultaResponseDTO> toResponseDTOList(List<InfoTeleconsulta> infoTeleconsultas) {
        if (infoTeleconsultas == null) {
            return List.of();
        }
        return infoTeleconsultas.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}