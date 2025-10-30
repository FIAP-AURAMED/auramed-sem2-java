package br.com.auramed.domain.service;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.PacienteCuidador;
import java.util.List;

public interface PacienteCuidadorService {
    PacienteCuidador associar(PacienteCuidador pacienteCuidador);
    void desassociar(Integer idPaciente, Integer idCuidador) throws EntidadeNaoLocalizadaException;
    PacienteCuidador localizarAssociacao(Integer idPaciente, Integer idCuidador) throws EntidadeNaoLocalizadaException;
    List<PacienteCuidador> listarCuidadoresDoPaciente(Integer idPaciente);
    List<PacienteCuidador> listarPacientesDoCuidador(Integer idCuidador);
    List<PacienteCuidador> listarTodasAssociacoes();
}