package br.com.auramed.domain.repository;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.Paciente;
import java.util.List;

public interface PacienteRepository {
    Paciente buscarPorId(Integer idPessoa) throws EntidadeNaoLocalizadaException;
    Paciente buscarPorCartaoSUS(String nrCartaoSUS) throws EntidadeNaoLocalizadaException;
    List<Paciente> buscarPorMedicoResponsavel(Integer idMedicoResponsavel);
    List<Paciente> buscarTodos();
    List<Paciente> buscarAtivos();
    Paciente salvar(Paciente paciente);
    Paciente editar(Paciente paciente);
    void remover(Integer idPessoa);
    boolean existeCartaoSUS(String nrCartaoSUS);
}