package br.com.auramed.domain.repository;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.Especialidade;
import java.util.List;

public interface EspecialidadeRepository {
    Especialidade buscarPorId(Integer id) throws EntidadeNaoLocalizadaException;
    Especialidade buscarPorNome(String nome) throws EntidadeNaoLocalizadaException;
    List<Especialidade> buscarTodos();
    List<Especialidade> buscarAtivas();
    List<Especialidade> buscarPorNomeContendo(String nome);
    Especialidade salvar(Especialidade especialidade);
    Especialidade editar(Especialidade especialidade);
    void remover(Integer id);
}