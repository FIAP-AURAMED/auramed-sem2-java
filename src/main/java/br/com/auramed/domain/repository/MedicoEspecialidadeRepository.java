package br.com.auramed.domain.repository;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.Medico;
import br.com.auramed.domain.model.Especialidade;
import java.util.List;

public interface MedicoEspecialidadeRepository {
    void associarEspecialidade(Integer idMedico, Integer idEspecialidade) throws EntidadeNaoLocalizadaException;
    void desassociarEspecialidade(Integer idMedico, Integer idEspecialidade) throws EntidadeNaoLocalizadaException;
    List<Especialidade> buscarEspecialidadesPorMedico(Integer idMedico);
    List<Medico> buscarMedicosPorEspecialidade(Integer idEspecialidade);
    boolean verificarAssociacao(Integer idMedico, Integer idEspecialidade);
    void removerTodasEspecialidadesDoMedico(Integer idMedico);
    void removerTodosMedicosDaEspecialidade(Integer idEspecialidade);
}