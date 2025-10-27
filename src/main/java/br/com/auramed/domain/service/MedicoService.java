package br.com.auramed.domain.service;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.Medico;
import java.util.List;

public interface MedicoService {
    Medico criar(Medico medico);
    Medico editar(Integer id, Medico medico) throws EntidadeNaoLocalizadaException;
    Medico remover(Integer id) throws EntidadeNaoLocalizadaException;
    Medico localizar(Integer id) throws EntidadeNaoLocalizadaException;
    Medico localizarPorCrm(String crm) throws EntidadeNaoLocalizadaException;
    List<Medico> listarTodos();
    List<Medico> listarPorEspecialidade(Integer idEspecialidade);
    Medico alterarStatusTeleconsulta(Integer id, String aceitaTeleconsulta) throws EntidadeNaoLocalizadaException;
}