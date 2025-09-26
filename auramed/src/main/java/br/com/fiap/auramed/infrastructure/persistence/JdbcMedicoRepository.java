package br.com.fiap.auramed.infrastructure.persistence;

import br.com.fiap.auramed.application.repository.MedicoRepository;
import br.com.fiap.auramed.domain.exception.EntidadeNaoLocalizada;
import br.com.fiap.auramed.domain.model.entity.Medico;

import java.util.List;

public class JdbcMedicoRepository implements MedicoRepository {
    @Override
    public Medico salvar(Medico medico) {
        return null;
    }

    @Override
    public Medico buscarPorCrm(String crm) throws EntidadeNaoLocalizada {
        return null;
    }

    @Override
    public Medico editar(Medico medico) {
        return null;
    }

    @Override
    public List<Medico> buscarTodos() {
        return List.of();
    }

    @Override
    public Medico desativar(Medico medico) {
        return null;
    }
}
