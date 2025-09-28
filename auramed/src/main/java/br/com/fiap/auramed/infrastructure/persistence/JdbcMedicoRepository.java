package br.com.fiap.auramed.infrastructure.persistence;

import br.com.fiap.auramed.application.repository.MedicoRepository;
import br.com.fiap.auramed.domain.model.entity.Medico;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcMedicoRepository implements MedicoRepository {
    private final DatabaseConnection databaseConnection;
    public JdbcMedicoRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public Medico salvar(Medico medico) throws SQLException {
        String pessoaSql = "INSERT INTO T_ARMD_PESSOA (nm_pessoa, nm_email, nr_cpf, dt_nascimento, st_genero, nr_telefone, tp_pessoa) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = this.databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(pessoaSql)) {

            stmt.setString(1, medico.getNomeCompleto().valor());
            stmt.setString(2, medico.getEmail().enderecoEmail());
            stmt.setString(3, medico.getCpf().numero());
            stmt.setDate(4, Date.valueOf(medico.getDataNascimento().data()));
            stmt.setString(5, String.valueOf(medico.getGenero().getSigla()));
            stmt.setString(6, medico.getTelefone().numero());
            stmt.setString(7, "MEDICO");
        }
        return null;
    }

    @Override
    public Optional<Medico> buscarPorCrm(String crm) {
        return Optional.empty();
    }

    @Override
    public List<Medico> buscarTodos() {
        return List.of();
    }

    @Override
    public void desativar(Long id) {

    }
}
