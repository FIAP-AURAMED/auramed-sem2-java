package br.com.auramed.domain.repository;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.AuthMedico;
import java.util.List;

public interface AuthMedicoRepository {
    AuthMedico buscarPorId(Integer id) throws EntidadeNaoLocalizadaException;
    AuthMedico buscarPorEmail(String email) throws EntidadeNaoLocalizadaException;
    AuthMedico buscarPorMedicoId(Integer medicoId) throws EntidadeNaoLocalizadaException;
    List<AuthMedico> buscarTodos();
    List<AuthMedico> buscarContasAtivas();
    List<AuthMedico> buscarContasBloqueadas();
    AuthMedico salvar(AuthMedico authMedico);
    AuthMedico editar(AuthMedico authMedico);
    void remover(Integer id);
    void removerPorMedicoId(Integer medicoId);
}