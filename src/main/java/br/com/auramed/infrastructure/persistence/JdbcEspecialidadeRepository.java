package br.com.auramed.infrastructure.persistence;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.Especialidade;
import br.com.auramed.domain.repository.EspecialidadeRepository;
import br.com.auramed.infrastructure.exception.InfrastructureException;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcEspecialidadeRepository implements EspecialidadeRepository {

    private final DatabaseConnection databaseConnection;

    @Inject
    public JdbcEspecialidadeRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    private Especialidade mapResultSetToEspecialidade(ResultSet rs) throws SQLException {
        Especialidade especialidade = new Especialidade(rs.getString("NM_ESPECIALIDADE"));
        especialidade.setId(rs.getInt("ID_ESPECIALIDADE"));
        especialidade.setDescricao(rs.getString("DS_ESPECIALIDADE"));
        especialidade.setAtivo(rs.getString("IN_ATIVO"));

        return especialidade;
    }

    @Override
    public Especialidade buscarPorId(Integer id) throws EntidadeNaoLocalizadaException {
        String sql = "SELECT * FROM T_ARMD_ESPECIALIDADE WHERE ID_ESPECIALIDADE = ? AND IN_ATIVO = 'S'";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEspecialidade(rs);
                } else {
                    throw new EntidadeNaoLocalizadaException("Especialidade não encontrada com ID: " + id);
                }
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar especialidade por ID: " + e.getMessage());
        }
    }

    @Override
    public Especialidade buscarPorNome(String nome) throws EntidadeNaoLocalizadaException {
        String sql = "SELECT * FROM T_ARMD_ESPECIALIDADE WHERE NM_ESPECIALIDADE = ? AND IN_ATIVO = 'S'";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEspecialidade(rs);
                } else {
                    throw new EntidadeNaoLocalizadaException("Especialidade não encontrada com nome: " + nome);
                }
            }
        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar especialidade por nome: " + e.getMessage());
        }
    }

    @Override
    public List<Especialidade> buscarTodos() {
        String sql = "SELECT * FROM T_ARMD_ESPECIALIDADE WHERE IN_ATIVO = 'S'";
        List<Especialidade> especialidades = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                especialidades.add(mapResultSetToEspecialidade(rs));
            }

            return especialidades;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar todas especialidades: " + e.getMessage());
        }
    }

    @Override
    public List<Especialidade> buscarAtivas() {
        return buscarTodos(); // Já filtra por ativas
    }

    @Override
    public List<Especialidade> buscarPorNomeContendo(String nome) {
        String sql = "SELECT * FROM T_ARMD_ESPECIALIDADE WHERE NM_ESPECIALIDADE LIKE ? AND IN_ATIVO = 'S'";
        List<Especialidade> especialidades = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    especialidades.add(mapResultSetToEspecialidade(rs));
                }
            }

            return especialidades;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar especialidades por nome contendo: " + e.getMessage());
        }
    }

    @Override
    public Especialidade salvar(Especialidade especialidade) {
        String sql = "INSERT INTO T_ARMD_ESPECIALIDADE (NM_ESPECIALIDADE, DS_ESPECIALIDADE, IN_ATIVO) " +
                "VALUES (?, ?, ?)";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_ESPECIALIDADE"})) {

            stmt.setString(1, especialidade.getNome());
            stmt.setString(2, especialidade.getDescricao());
            stmt.setString(3, especialidade.getAtivo());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new InfrastructureException("Nenhuma linha afetada ao salvar especialidade.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    especialidade.setId(generatedKeys.getInt(1));
                }
            }
            return especialidade;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao salvar especialidade: " + e.getMessage());
        }
    }

    @Override
    public Especialidade editar(Especialidade especialidade) throws EntidadeNaoLocalizadaException {
        if (especialidade.getId() == null) {
            throw new IllegalArgumentException("ID da especialidade não pode ser nulo para edição");
        }

        String sql = "UPDATE T_ARMD_ESPECIALIDADE SET NM_ESPECIALIDADE = ?, DS_ESPECIALIDADE = ?, IN_ATIVO = ? " +
                "WHERE ID_ESPECIALIDADE = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, especialidade.getNome());
            stmt.setString(2, especialidade.getDescricao());
            stmt.setString(3, especialidade.getAtivo());
            stmt.setInt(4, especialidade.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new EntidadeNaoLocalizadaException("Especialidade não encontrada com ID: " + especialidade.getId());
            }

            return especialidade;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao editar especialidade: " + e.getMessage());
        }
    }

    @Override
    public void remover(Integer id) throws EntidadeNaoLocalizadaException {
        String sql = "UPDATE T_ARMD_ESPECIALIDADE SET IN_ATIVO = 'N' WHERE ID_ESPECIALIDADE = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new EntidadeNaoLocalizadaException("Especialidade não encontrada com ID: " + id);
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao remover especialidade: " + e.getMessage());
        }
    }
}