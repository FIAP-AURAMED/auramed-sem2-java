package br.com.auramed.interfaces.controllers;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.interfaces.dto.request.AuthMedicoRequestDTO;
import br.com.auramed.interfaces.dto.response.AuthMedicoResponseDTO;
import java.util.List;

public interface AuthMedicoController {
    AuthMedicoResponseDTO criarAuthMedico(AuthMedicoRequestDTO authMedicoRequest) throws EntidadeNaoLocalizadaException;
    AuthMedicoResponseDTO editarAuthMedico(Integer id, AuthMedicoRequestDTO authMedicoRequest) throws EntidadeNaoLocalizadaException;
    AuthMedicoResponseDTO getAuthMedicoById(Integer id) throws EntidadeNaoLocalizadaException;
    AuthMedicoResponseDTO getAuthMedicoByEmail(String email) throws EntidadeNaoLocalizadaException;
    AuthMedicoResponseDTO getAuthMedicoByMedicoId(Integer medicoId) throws EntidadeNaoLocalizadaException;
    void deleteAuthMedico(Integer id) throws EntidadeNaoLocalizadaException;
    List<AuthMedicoResponseDTO> getAllAuthMedicos();
    void validarCredenciais(String email, String senha) throws EntidadeNaoLocalizadaException;
    AuthMedicoResponseDTO atualizarSenha(Integer id, String novaSenhaHash) throws EntidadeNaoLocalizadaException;
    AuthMedicoResponseDTO bloquearConta(Integer id) throws EntidadeNaoLocalizadaException;
    AuthMedicoResponseDTO ativarConta(Integer id) throws EntidadeNaoLocalizadaException;
}