package br.com.auramed.interfaces.mappers;

import br.com.auramed.interfaces.dto.request.PacienteCompletoRequestDTO;
import br.com.auramed.interfaces.dto.response.PacienteCompletoResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PacienteCompletoMapperImpl implements PacienteCompletoMapper {

    @Inject
    PessoaMapper pessoaMapper;

    @Inject
    PacienteMapper pacienteMapper;

    @Inject
    InfoTeleconsultaMapper infoTeleconsultaMapper;

    @Inject
    PerfilCognitivoMapper perfilCognitivoMapper;

    @Override
    public PacienteCompletoResponseDTO toResponseDTO(PacienteCompletoRequestDTO request) {
        if (request == null) {
            return null;
        }

        PacienteCompletoResponseDTO response = new PacienteCompletoResponseDTO();

        if (request.getPessoa() != null) {
            response.setPessoa(pessoaMapper.toResponseDTO(pessoaMapper.toDomain(request.getPessoa())));
        }

        if (request.getPaciente() != null) {
            response.setPaciente(pacienteMapper.toResponseDTO(pacienteMapper.toDomain(request.getPaciente())));
        }

        if (request.getInfoTeleconsulta() != null) {
            response.setInfoTeleconsulta(infoTeleconsultaMapper.toResponseDTO(
                    infoTeleconsultaMapper.toDomain(request.getInfoTeleconsulta())));
        }

        if (request.getPerfilCognitivo() != null) {
            response.setPerfilCognitivo(perfilCognitivoMapper.toResponseDTO(
                    perfilCognitivoMapper.toDomain(request.getPerfilCognitivo())));
        }

        return response;
    }
}