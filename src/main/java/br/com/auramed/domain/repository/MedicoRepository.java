package br.com.auramed.domain.repository;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.Medico;
import java.util.List;

public interface MedicoRepository {
    Medico buscarPorId(Integer id) throws EntidadeNaoLocalizadaException;
    Medico buscarPorCrm(String crm) throws EntidadeNaoLocalizadaException;
    Medico buscarPorPessoaId(Integer pessoaId) throws EntidadeNaoLocalizadaException;
    List<Medico> buscarTodos();
    List<Medico> buscarPorEspecialidade(Integer idEspecialidade);
    List<Medico> buscarPorAceitaTeleconsulta(String aceitaTeleconsulta);
    List<Medico> buscarAtivos();
    Medico salvar(Medico medico);
    Medico editar(Medico medico);
    void remover(Integer id);
}