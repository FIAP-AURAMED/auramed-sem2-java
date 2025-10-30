package br.com.auramed.domain.repository;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.PacienteCuidador;
import java.util.List;

public interface PacienteCuidadorRepository {
    PacienteCuidador buscarPorIds(Integer idPaciente, Integer idCuidador) throws EntidadeNaoLocalizadaException;
    List<PacienteCuidador> buscarPorPaciente(Integer idPaciente);
    List<PacienteCuidador> buscarPorCuidador(Integer idCuidador);
    List<PacienteCuidador> buscarTodos();
    PacienteCuidador salvar(PacienteCuidador pacienteCuidador);
    void remover(Integer idPaciente, Integer idCuidador);
    boolean existeAssociacao(Integer idPaciente, Integer idCuidador);
}