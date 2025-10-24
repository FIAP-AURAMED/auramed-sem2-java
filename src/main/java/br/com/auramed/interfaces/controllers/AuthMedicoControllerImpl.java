package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.AuthMedico;
import br.com.auramed.domain.service.AuthMedicoService;
import br.com.auramed.interfaces.dto.request.AuthMedicoRequestDTO;
import br.com.auramed.interfaces.dto.response.AuthMedicoResponseDTO;
import br.com.auramed.interfaces.mappers.AuthMedicoMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class AuthMedicoControllerImpl implements AuthMedicoController {
    private final AuthMedicoService authMedicoService;
    private final AuthMedicoMapper authMedicoMapper;

    @Inject
    public AuthMedicoControllerImpl(AuthMedicoService authMedicoService, AuthMedicoMapper authMedicoMapper) {
        this.authMedicoService = authMedicoService;
        this.authMedicoMapper = authMedicoMapper;
    }

    @Override
    public AuthMedicoResponseDTO criarAuthMedico(AuthMedicoRequestDTO authMedicoRequest) throws EntidadeNaoLocalizadaException {
        try {
            AuthMedico authMedico = authMedicoMapper.toDomain(authMedicoRequest);
            AuthMedico authMedicoCriado = this.authMedicoService.criar(authMedico);
            return authMedicoMapper.toResponseDTO(authMedicoCriado);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public AuthMedicoResponseDTO editarAuthMedico(Integer id, AuthMedicoRequestDTO authMedicoRequest) throws EntidadeNaoLocalizadaException {
        AuthMedico authMedicoExistente = this.authMedicoService.localizar(id);

        authMedicoExistente.setEmail(authMedicoRequest.getEmail());
        authMedicoExistente.setSenhaHash(authMedicoRequest.getSenha());

        AuthMedico authMedicoAtualizado = this.authMedicoService.editar(id, authMedicoExistente);
        return authMedicoMapper.toResponseDTO(authMedicoAtualizado);
    }

    @Override
    public AuthMedicoResponseDTO getAuthMedicoById(Integer id) throws EntidadeNaoLocalizadaException {
        AuthMedico authMedico = this.authMedicoService.localizar(id);
        return authMedicoMapper.toResponseDTO(authMedico);
    }

    @Override
    public AuthMedicoResponseDTO getAuthMedicoByEmail(String email) throws EntidadeNaoLocalizadaException {
        AuthMedico authMedico = this.authMedicoService.localizarPorEmail(email);
        return authMedicoMapper.toResponseDTO(authMedico);
    }

    @Override
    public AuthMedicoResponseDTO getAuthMedicoByMedicoId(Integer medicoId) throws EntidadeNaoLocalizadaException {
        AuthMedico authMedico = this.authMedicoService.localizarPorMedicoId(medicoId);
        return authMedicoMapper.toResponseDTO(authMedico);
    }

    @Override
    public void deleteAuthMedico(Integer id) throws EntidadeNaoLocalizadaException {
        this.authMedicoService.remover(id);
    }

    @Override
    public List<AuthMedicoResponseDTO> getAllAuthMedicos() {
        List<AuthMedico> authMedicos = authMedicoService.listarTodos();
        return authMedicoMapper.toResponseDTOList(authMedicos);
    }

    @Override
    public void validarCredenciais(String email, String senha) throws EntidadeNaoLocalizadaException {
        this.authMedicoService.validarCredenciais(email, senha);
    }

    @Override
    public AuthMedicoResponseDTO atualizarSenha(Integer id, String novaSenhaHash) throws EntidadeNaoLocalizadaException {
        AuthMedico authMedicoAtualizado = this.authMedicoService.atualizarSenha(id, novaSenhaHash);
        return authMedicoMapper.toResponseDTO(authMedicoAtualizado);
    }

    @Override
    public AuthMedicoResponseDTO bloquearConta(Integer id) throws EntidadeNaoLocalizadaException {
        AuthMedico authMedicoAtualizado = this.authMedicoService.bloquearConta(id);
        return authMedicoMapper.toResponseDTO(authMedicoAtualizado);
    }

    @Override
    public AuthMedicoResponseDTO ativarConta(Integer id) throws EntidadeNaoLocalizadaException {
        AuthMedico authMedicoAtualizado = this.authMedicoService.ativarConta(id);
        return authMedicoMapper.toResponseDTO(authMedicoAtualizado);
    }
}