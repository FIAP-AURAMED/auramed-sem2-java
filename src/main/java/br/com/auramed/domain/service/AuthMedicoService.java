package br.com.auramed.domain.service;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.AuthMedico;

import java.util.List;

public interface AuthMedicoService {
    AuthMedico criar(AuthMedico authMedico);
    AuthMedico editar(Integer id, AuthMedico authMedico) throws EntidadeNaoLocalizadaException;
    AuthMedico remover(Integer id) throws EntidadeNaoLocalizadaException;
    AuthMedico localizar(Integer id) throws EntidadeNaoLocalizadaException;
    AuthMedico localizarPorEmail(String email) throws EntidadeNaoLocalizadaException;
    AuthMedico localizarPorMedicoId(Integer medicoId) throws EntidadeNaoLocalizadaException;
    List<AuthMedico> listarTodos();
    void validarCredenciais(String email, String senha) throws EntidadeNaoLocalizadaException;
    AuthMedico atualizarSenha(Integer id, String novaSenhaHash) throws EntidadeNaoLocalizadaException;
    AuthMedico registrarLoginSucesso(Integer id);
    AuthMedico registrarTentativaLoginFalha(Integer id);
    AuthMedico bloquearConta(Integer id) throws EntidadeNaoLocalizadaException;
    AuthMedico ativarConta(Integer id) throws EntidadeNaoLocalizadaException;
}