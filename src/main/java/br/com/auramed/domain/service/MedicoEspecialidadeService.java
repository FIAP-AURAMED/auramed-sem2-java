package br.com.auramed.domain.service;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.Medico;
import br.com.auramed.domain.model.Especialidade;
import java.util.List;

public interface MedicoEspecialidadeService {
    void associarEspecialidade(Integer idMedico, Integer idEspecialidade) throws EntidadeNaoLocalizadaException;
    void desassociarEspecialidade(Integer idMedico, Integer idEspecialidade) throws EntidadeNaoLocalizadaException;
    List<Especialidade> listarEspecialidadesPorMedico(Integer idMedico);
    List<Medico> listarMedicosPorEspecialidade(Integer idEspecialidade);
    boolean verificarAssociacao(Integer idMedico, Integer idEspecialidade);
}