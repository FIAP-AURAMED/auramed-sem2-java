package br.com.fiap.auramed.application.repository;

import br.com.fiap.auramed.domain.model.entity.Medico;
import java.util.List;
import java.util.Optional;

public interface MedicoRepository {
    Medico salvar(Medico medico);
    Optional<Medico> buscarPorCrm(String crm);
    Optional<Medico> buscarPorId(Long id);
    List<Medico> buscarTodos();
    void desativar(Long id);
    Medico atualizar(Medico medico);
}