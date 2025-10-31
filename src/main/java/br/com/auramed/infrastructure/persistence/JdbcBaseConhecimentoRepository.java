package br.com.auramed.infrastructure.persistence;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.BaseConhecimento;
import br.com.auramed.domain.repository.BaseConhecimentoRepository;
import br.com.auramed.infrastructure.exception.InfrastructureException;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcBaseConhecimentoRepository implements BaseConhecimentoRepository {

    private final DatabaseConnection databaseConnection;

    @Inject
    public JdbcBaseConhecimentoRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    private BaseConhecimento mapResultSetToBaseConhecimento(ResultSet rs) throws SQLException {
        BaseConhecimento conhecimento = new BaseConhecimento();
        conhecimento.setId(String.valueOf(rs.getInt("ID_BASE_CONHECIMENTO")));
        conhecimento.setPergunta(rs.getString("DS_PERGUNTA"));
        conhecimento.setResposta(rs.getString("DS_RESPOSTA"));
        conhecimento.setCategoria(rs.getString("TP_CATEGORIA"));
        conhecimento.setPalavrasChave(rs.getString("DS_PALAVRAS_CHAVE"));
        conhecimento.setConfianca(rs.getDouble("NR_CONFIANCA"));
        return conhecimento;
    }

    @Override
    public BaseConhecimento buscarPorPalavrasChave(String pergunta) {
        String sql = "SELECT * FROM T_ARMD_BASE_CONHECIMENTO WHERE DS_PALAVRAS_CHAVE LIKE ? AND IN_ATIVO = 'S'";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + pergunta + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBaseConhecimento(rs);
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar base conhecimento por palavras-chave: " + e.getMessage());
        }
    }

    @Override
    public BaseConhecimento buscarPorSimilaridade(String pergunta) {
        String sql = "SELECT * FROM T_ARMD_BASE_CONHECIMENTO WHERE UPPER(DS_PERGUNTA) LIKE UPPER(?) AND IN_ATIVO = 'S'";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + pergunta + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBaseConhecimento(rs);
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar base conhecimento por similaridade: " + e.getMessage());
        }
    }

    @Override
    public BaseConhecimento buscarPorId(String id) throws EntidadeNaoLocalizadaException {
        String sql = "SELECT * FROM T_ARMD_BASE_CONHECIMENTO WHERE ID_BASE_CONHECIMENTO = ? AND IN_ATIVO = 'S'";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBaseConhecimento(rs);
                } else {
                    throw new EntidadeNaoLocalizadaException("BaseConhecimento não encontrada com ID: " + id);
                }
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar base conhecimento por ID: " + e.getMessage());
        }
    }

    @Override
    public List<BaseConhecimento> buscarTodos() {
        String sql = "SELECT * FROM T_ARMD_BASE_CONHECIMENTO WHERE IN_ATIVO = 'S' ORDER BY ID_BASE_CONHECIMENTO";
        List<BaseConhecimento> conhecimentos = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                conhecimentos.add(mapResultSetToBaseConhecimento(rs));
            }

            return conhecimentos;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar todas base conhecimento: " + e.getMessage());
        }
    }

    @Override
    public List<BaseConhecimento> buscarPorCategoria(String categoria) {
        String sql = "SELECT * FROM T_ARMD_BASE_CONHECIMENTO WHERE TP_CATEGORIA = ? AND IN_ATIVO = 'S' ORDER BY ID_BASE_CONHECIMENTO";
        List<BaseConhecimento> conhecimentos = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    conhecimentos.add(mapResultSetToBaseConhecimento(rs));
                }
            }

            return conhecimentos;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar base conhecimento por categoria: " + e.getMessage());
        }
    }

    @Override
    public BaseConhecimento salvar(BaseConhecimento conhecimento) {
        String sql = "INSERT INTO T_ARMD_BASE_CONHECIMENTO (DS_PERGUNTA, DS_RESPOSTA, TP_CATEGORIA, DS_PALAVRAS_CHAVE, NR_CONFIANCA, DT_CADASTRO, IN_ATIVO) " +
                "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_BASE_CONHECIMENTO"})) {

            stmt.setString(1, conhecimento.getPergunta());
            stmt.setString(2, conhecimento.getResposta());
            stmt.setString(3, conhecimento.getCategoria());
            stmt.setString(4, conhecimento.getPalavrasChave());
            stmt.setDouble(5, conhecimento.getConfianca());
            stmt.setString(6, "S");

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new InfrastructureException("Nenhuma linha afetada ao salvar base conhecimento.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    conhecimento.setId(String.valueOf(generatedKeys.getInt(1)));
                }
            }
            return conhecimento;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao salvar base conhecimento: " + e.getMessage());
        }
    }

    @Override
    public BaseConhecimento editar(BaseConhecimento conhecimento) {
        if (conhecimento.getId() == null) {
            throw new IllegalArgumentException("ID da base conhecimento não pode ser nulo para edição");
        }

        String sql = "UPDATE T_ARMD_BASE_CONHECIMENTO SET DS_PERGUNTA = ?, DS_RESPOSTA = ?, TP_CATEGORIA = ?, " +
                "DS_PALAVRAS_CHAVE = ?, NR_CONFIANCA = ? WHERE ID_BASE_CONHECIMENTO = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, conhecimento.getPergunta());
            stmt.setString(2, conhecimento.getResposta());
            stmt.setString(3, conhecimento.getCategoria());
            stmt.setString(4, conhecimento.getPalavrasChave());
            stmt.setDouble(5, conhecimento.getConfianca());
            stmt.setInt(6, Integer.parseInt(conhecimento.getId()));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new EntidadeNaoLocalizadaException("BaseConhecimento não encontrada com ID: " + conhecimento.getId());
            }

            return conhecimento;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao editar base conhecimento: " + e.getMessage());
        }
    }

    @Override
    public void remover(String id) {
        String sql = "UPDATE T_ARMD_BASE_CONHECIMENTO SET IN_ATIVO = 'N' WHERE ID_BASE_CONHECIMENTO = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new EntidadeNaoLocalizadaException("BaseConhecimento não encontrada com ID: " + id);
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao remover base conhecimento: " + e.getMessage());
        }
    }
}