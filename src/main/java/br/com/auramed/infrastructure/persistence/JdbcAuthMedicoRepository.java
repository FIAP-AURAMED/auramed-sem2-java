package br.com.auramed.infrastructure.persistence;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.AuthMedico;
import br.com.auramed.domain.model.Medico;
import br.com.auramed.domain.repository.AuthMedicoRepository;
import br.com.auramed.infrastructure.exception.InfrastructureException;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcAuthMedicoRepository implements AuthMedicoRepository {

    private final DatabaseConnection databaseConnection;

    @Inject
    public JdbcAuthMedicoRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    private AuthMedico mapResultSetToAuthMedico(ResultSet rs) throws SQLException {
        Medico medico = new Medico(null, null);
        medico.setId(rs.getInt("T_ARMD_MEDICO_id_pessoa"));

        AuthMedico authMedico = new AuthMedico(medico, rs.getString("NM_EMAIL"), rs.getString("NM_SENHA_HASH"));
        authMedico.setId(rs.getInt("ID_AUTH"));

        Timestamp ultimoLogin = rs.getTimestamp("DT_ULTIMO_LOGIN");
        if (ultimoLogin != null) {
            authMedico.setUltimoLogin(ultimoLogin.toLocalDateTime());
        }

        authMedico.setTentativasLogin(rs.getInt("QT_TENTATIVAS_LOGIN"));
        authMedico.setContaAtiva(rs.getString("IN_CONTA_ATIVA"));
        authMedico.setDataCriacao(rs.getTimestamp("DT_CRIACAO").toLocalDateTime());
        authMedico.setDataAlteracaoSenha(rs.getTimestamp("DT_ALTERACAO_SENHA").toLocalDateTime());

        return authMedico;
    }

    @Override
    public AuthMedico buscarPorId(Integer id) throws EntidadeNaoLocalizadaException {
        String sql = "SELECT * FROM T_ARMD_AUTH_MEDICO WHERE ID_AUTH = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAuthMedico(rs);
                } else {
                    throw new EntidadeNaoLocalizadaException("Auth médico não encontrado com ID: " + id);
                }
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar auth médico por ID: " + e.getMessage());
        }
    }

    @Override
    public AuthMedico buscarPorEmail(String email) throws EntidadeNaoLocalizadaException {
        String sql = "SELECT * FROM T_ARMD_AUTH_MEDICO WHERE NM_EMAIL = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAuthMedico(rs);
                } else {
                    throw new EntidadeNaoLocalizadaException("Auth médico não encontrado com email: " + email);
                }
            }
        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar auth médico por email: " + e.getMessage());
        }
    }

    @Override
    public AuthMedico buscarPorMedicoId(Integer medicoId) throws EntidadeNaoLocalizadaException {
        String sql = "SELECT * FROM T_ARMD_AUTH_MEDICO WHERE T_ARMD_MEDICO_id_pessoa = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, medicoId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAuthMedico(rs);
                } else {
                    throw new EntidadeNaoLocalizadaException("Auth médico não encontrado para médico ID: " + medicoId);
                }
            }
        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar auth médico por médico ID: " + e.getMessage());
        }
    }

    @Override
    public List<AuthMedico> buscarTodos() {
        String sql = "SELECT * FROM T_ARMD_AUTH_MEDICO";
        List<AuthMedico> authMedicos = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                authMedicos.add(mapResultSetToAuthMedico(rs));
            }

            return authMedicos;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar todos auth médicos: " + e.getMessage());
        }
    }

    @Override
    public List<AuthMedico> buscarContasAtivas() {
        String sql = "SELECT * FROM T_ARMD_AUTH_MEDICO WHERE IN_CONTA_ATIVA = 'S'";
        List<AuthMedico> authMedicos = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                authMedicos.add(mapResultSetToAuthMedico(rs));
            }

            return authMedicos;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar contas ativas: " + e.getMessage());
        }
    }

    @Override
    public List<AuthMedico> buscarContasBloqueadas() {
        String sql = "SELECT * FROM T_ARMD_AUTH_MEDICO WHERE IN_CONTA_ATIVA = 'N'";
        List<AuthMedico> authMedicos = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                authMedicos.add(mapResultSetToAuthMedico(rs));
            }

            return authMedicos;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar contas bloqueadas: " + e.getMessage());
        }
    }

    @Override
    public AuthMedico salvar(AuthMedico authMedico) {
        String sql = "INSERT INTO T_ARMD_AUTH_MEDICO (T_ARMD_MEDICO_id_pessoa, NM_EMAIL, NM_SENHA_HASH, " +
                "QT_TENTATIVAS_LOGIN, IN_CONTA_ATIVA, DT_CRIACAO, DT_ALTERACAO_SENHA) " +
                "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_AUTH"})) {

            stmt.setInt(1, authMedico.getMedico().getId());
            stmt.setString(2, authMedico.getEmail());
            stmt.setString(3, authMedico.getSenhaHash());
            stmt.setInt(4, authMedico.getTentativasLogin());
            stmt.setString(5, authMedico.getContaAtiva());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new InfrastructureException("Nenhuma linha afetada ao salvar auth médico.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    authMedico.setId(generatedKeys.getInt(1));
                }
            }
            return authMedico;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao salvar auth médico: " + e.getMessage());
        }
    }

    @Override
    public AuthMedico editar(AuthMedico authMedico) throws EntidadeNaoLocalizadaException {
        if (authMedico.getId() == null) {
            throw new IllegalArgumentException("ID do auth médico não pode ser nulo para edição");
        }

        String sql = "UPDATE T_ARMD_AUTH_MEDICO SET NM_EMAIL = ?, NM_SENHA_HASH = ?, DT_ULTIMO_LOGIN = ?, " +
                "QT_TENTATIVAS_LOGIN = ?, IN_CONTA_ATIVA = ?, DT_ALTERACAO_SENHA = ? WHERE ID_AUTH = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, authMedico.getEmail());
            stmt.setString(2, authMedico.getSenhaHash());

            if (authMedico.getUltimoLogin() != null) {
                stmt.setTimestamp(3, Timestamp.valueOf(authMedico.getUltimoLogin()));
            } else {
                stmt.setNull(3, Types.TIMESTAMP);
            }

            stmt.setInt(4, authMedico.getTentativasLogin());
            stmt.setString(5, authMedico.getContaAtiva());
            stmt.setTimestamp(6, Timestamp.valueOf(authMedico.getDataAlteracaoSenha()));
            stmt.setInt(7, authMedico.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new EntidadeNaoLocalizadaException("Auth médico não encontrado com ID: " + authMedico.getId());
            }

            return authMedico;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao editar auth médico: " + e.getMessage());
        }
    }

    @Override
    public void remover(Integer id) throws EntidadeNaoLocalizadaException {
        String sql = "DELETE FROM T_ARMD_AUTH_MEDICO WHERE ID_AUTH = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new EntidadeNaoLocalizadaException("Auth médico não encontrado com ID: " + id);
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao remover auth médico: " + e.getMessage());
        }
    }

    @Override
    public void removerPorMedicoId(Integer medicoId) throws EntidadeNaoLocalizadaException {
        String sql = "DELETE FROM T_ARMD_AUTH_MEDICO WHERE T_ARMD_MEDICO_id_pessoa = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, medicoId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new EntidadeNaoLocalizadaException("Auth médico não encontrado para médico ID: " + medicoId);
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao remover auth médico por médico ID: " + e.getMessage());
        }
    }
}