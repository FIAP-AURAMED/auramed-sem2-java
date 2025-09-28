package br.com.fiap.auramed.application.repository;

import br.com.fiap.auramed.domain.model.entity.Medico;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MedicoRepository {
    Medico salvar(Medico medico) throws SQLException;
    Optional<Medico> buscarPorCrm(String crm);
    List<Medico> buscarTodos();
    void desativar(Long id);
}
