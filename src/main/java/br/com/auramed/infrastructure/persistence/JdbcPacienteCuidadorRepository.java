package br.com.auramed.infrastructure.persistence;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.PacienteCuidador;
import br.com.auramed.domain.repository.PacienteCuidadorRepository;
import br.com.auramed.infrastructure.exception.InfrastructureException;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcPacienteCuidadorRepository implements PacienteCuidadorRepository {

    private final DatabaseConnection databaseConnection;

    @Inject
    public JdbcPacienteCuidadorRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    private PacienteCuidador mapResultSetToPacienteCuidador(ResultSet rs) throws SQLException {
        PacienteCuidador pacienteCuidador = new PacienteCuidador(
                rs.getInt("T_ARMD_PACIENTE_id_pessoa"),
                rs.getInt("T_ARMD_CUIDADOR_id_pessoa"),
                rs.getString("ds_tipo_relacionamento")
        );
        pacienteCuidador.setDataAssociacao(rs.getTimestamp("dt_associacao").toLocalDateTime());
        pacienteCuidador.setAtivo(rs.getString("in_ativo"));
        return pacienteCuidador;
    }

    @Override
    public PacienteCuidador buscarPorIds(Integer idPaciente, Integer idCuidador) throws EntidadeNaoLocalizadaException {
        String sql = "SELECT * FROM T_ARMD_PACIENTE_CUIDADOR WHERE T_ARMD_PACIENTE_id_pessoa = ? AND T_ARMD_CUIDADOR_id_pessoa = ? AND in_ativo = 'S'";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPaciente);
            stmt.setInt(2, idCuidador);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPacienteCuidador(rs);
                } else {
                    throw new EntidadeNaoLocalizadaException("Associação paciente-cuidador não encontrada");
                }
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar associação paciente-cuidador: " + e.getMessage());
        }
    }

    @Override
    public List<PacienteCuidador> buscarPorPaciente(Integer idPaciente) {
        String sql = "SELECT * FROM T_ARMD_PACIENTE_CUIDADOR WHERE T_ARMD_PACIENTE_id_pessoa = ? AND in_ativo = 'S'";
        List<PacienteCuidador> associacoes = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPaciente);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    associacoes.add(mapResultSetToPacienteCuidador(rs));
                }
            }

            return associacoes;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar associações por paciente: " + e.getMessage());
        }
    }

    @Override
    public List<PacienteCuidador> buscarPorCuidador(Integer idCuidador) {
        String sql = "SELECT * FROM T_ARMD_PACIENTE_CUIDADOR WHERE T_ARMD_CUIDADOR_id_pessoa = ? AND in_ativo = 'S'";
        List<PacienteCuidador> associacoes = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCuidador);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    associacoes.add(mapResultSetToPacienteCuidador(rs));
                }
            }

            return associacoes;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar associações por cuidador: " + e.getMessage());
        }
    }

    @Override
    public List<PacienteCuidador> buscarTodos() {
        String sql = "SELECT * FROM T_ARMD_PACIENTE_CUIDADOR WHERE in_ativo = 'S'";
        List<PacienteCuidador> associacoes = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                associacoes.add(mapResultSetToPacienteCuidador(rs));
            }

            return associacoes;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar todas associações: " + e.getMessage());
        }
    }

    @Override
    public PacienteCuidador salvar(PacienteCuidador pacienteCuidador) {
        String sql = "INSERT INTO T_ARMD_PACIENTE_CUIDADOR (T_ARMD_PACIENTE_id_pessoa, T_ARMD_CUIDADOR_id_pessoa, ds_tipo_relacionamento, dt_associacao, in_ativo) " +
                "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?)";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pacienteCuidador.getIdPaciente());
            stmt.setInt(2, pacienteCuidador.getIdCuidador());
            stmt.setString(3, pacienteCuidador.getTipoRelacionamento());
            stmt.setString(4, pacienteCuidador.getAtivo());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new InfrastructureException("Nenhuma linha afetada ao salvar associação paciente-cuidador.");
            }

            return pacienteCuidador;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao salvar associação paciente-cuidador: " + e.getMessage());
        }
    }

    @Override
    public void remover(Integer idPaciente, Integer idCuidador) throws EntidadeNaoLocalizadaException {
        String sql = "UPDATE T_ARMD_PACIENTE_CUIDADOR SET in_ativo = 'N' WHERE T_ARMD_PACIENTE_id_pessoa = ? AND T_ARMD_CUIDADOR_id_pessoa = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPaciente);
            stmt.setInt(2, idCuidador);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new EntidadeNaoLocalizadaException("Associação paciente-cuidador não encontrada");
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao remover associação paciente-cuidador: " + e.getMessage());
        }
    }

    @Override
    public boolean existeAssociacao(Integer idPaciente, Integer idCuidador) {
        String sql = "SELECT 1 FROM T_ARMD_PACIENTE_CUIDADOR WHERE T_ARMD_PACIENTE_id_pessoa = ? AND T_ARMD_CUIDADOR_id_pessoa = ? AND in_ativo = 'S'";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPaciente);
            stmt.setInt(2, idCuidador);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao verificar existência da associação: " + e.getMessage());
        }
    }
}