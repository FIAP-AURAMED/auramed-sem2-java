package br.com.auramed.domain.service;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.Especialidade;
import java.util.List;

public interface EspecialidadeService {
    Especialidade criar(Especialidade especialidade);
    Especialidade editar(Integer id, Especialidade especialidade) throws EntidadeNaoLocalizadaException;
    Especialidade remover(Integer id) throws EntidadeNaoLocalizadaException;
    Especialidade localizar(Integer id) throws EntidadeNaoLocalizadaException;
    Especialidade localizarPorNome(String nome) throws EntidadeNaoLocalizadaException;
    List<Especialidade> listarTodos();
    List<Especialidade> listarAtivas();
    Especialidade ativar(Integer id) throws EntidadeNaoLocalizadaException;
    Especialidade inativar(Integer id) throws EntidadeNaoLocalizadaException;
}