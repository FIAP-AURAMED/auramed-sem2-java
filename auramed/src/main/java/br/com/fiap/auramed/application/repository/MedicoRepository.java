package br.com.fiap.auramed.application.repository;

import br.com.fiap.auramed.domain.exception.EntidadeNaoLocalizada;
import br.com.fiap.auramed.domain.model.entity.Medico;

import java.util.List;

public interface MedicoRepository {
    Medico salvar(Medico medico);
    Medico buscarPorCrm(String crm) throws EntidadeNaoLocalizada;
    Medico editar(Medico medico);
    List<Medico> buscarTodos();
    Medico desativar(Medico medico);
}
