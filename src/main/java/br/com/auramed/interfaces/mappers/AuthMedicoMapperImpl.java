package br.com.auramed.interfaces.mappers;

import br.com.auramed.domain.model.AuthMedico;
import br.com.auramed.domain.model.Medico;
import br.com.auramed.interfaces.dto.request.AuthMedicoRequestDTO;
import br.com.auramed.interfaces.dto.response.AuthMedicoResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AuthMedicoMapperImpl implements AuthMedicoMapper {

    @Override
    public AuthMedico toDomain(AuthMedicoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Medico medico = new Medico(null, null);
        medico.setId(dto.getMedicoId());

        AuthMedico authMedico = new AuthMedico(medico, dto.getEmail(), dto.getSenha());

        if (dto.getId() != null) {
            authMedico.setId(dto.getId());
        } else {
            authMedico.setId(0);
        }

        return authMedico;
    }

    @Override
    public AuthMedicoResponseDTO toResponseDTO(AuthMedico authMedico) {
        if (authMedico == null) {
            return null;
        }

        AuthMedicoResponseDTO response = new AuthMedicoResponseDTO();
        response.setId(authMedico.getId());
        response.setMedicoId(authMedico.getMedico().getId());
        response.setEmail(authMedico.getEmail());
        response.setUltimoLogin(authMedico.getUltimoLogin());
        response.setTentativasLogin(authMedico.getTentativasLogin());
        response.setContaAtiva(authMedico.getContaAtiva());
        response.setDataCriacao(authMedico.getDataCriacao());
        response.setDataAlteracaoSenha(authMedico.getDataAlteracaoSenha());

        return response;
    }

    @Override
    public List<AuthMedicoResponseDTO> toResponseDTOList(List<AuthMedico> authMedicos) {
        if (authMedicos == null) {
            return List.of();
        }
        return authMedicos.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}