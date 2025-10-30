package br.com.auramed.infrastructure.persistence;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.Medico;
import br.com.auramed.domain.model.Especialidade;
import br.com.auramed.domain.repository.MedicoEspecialidadeRepository;
import br.com.auramed.infrastructure.exception.InfrastructureException;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcMedicoEspecialidadeRepository implements MedicoEspecialidadeRepository {

    private final DatabaseConnection databaseConnection;

    @Inject
    public JdbcMedicoEspecialidadeRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void associarEspecialidade(Integer idMedico, Integer idEspecialidade) throws EntidadeNaoLocalizadaException {
        String sql = "INSERT INTO T_ARMD_MEDICO_ESPECIALIDADE (T_ARMD_MEDICO_ID_PESSOA, T_ARMD_ESPECIALIDADE_ID_ESPECIALIDADE, DT_ASSOCIACAO) " +
                "VALUES (?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMedico);
            stmt.setInt(2, idEspecialidade);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new InfrastructureException("Nenhuma linha afetada ao associar especialidade.");
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao associar especialidade: " + e.getMessage());
        }
    }

    @Override
    public void desassociarEspecialidade(Integer idMedico, Integer idEspecialidade) throws EntidadeNaoLocalizadaException {
        String sql = "DELETE FROM T_ARMD_MEDICO_ESPECIALIDADE " +
                "WHERE T_ARMD_MEDICO_ID_PESSOA = ? AND T_ARMD_ESPECIALIDADE_ID_ESPECIALIDADE = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMedico);
            stmt.setInt(2, idEspecialidade);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new EntidadeNaoLocalizadaException("Associação não encontrada para médico ID: " + idMedico + " e especialidade ID: " + idEspecialidade);
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao desassociar especialidade: " + e.getMessage());
        }
    }

    @Override
    public List<Especialidade> buscarEspecialidadesPorMedico(Integer idMedico) {
        String sql = "SELECT e.ID_ESPECIALIDADE, e.NM_ESPECIALIDADE, e.DS_ESPECIALIDADE, e.IN_ATIVO " +
                "FROM T_ARMD_ESPECIALIDADE e " +
                "INNER JOIN T_ARMD_MEDICO_ESPECIALIDADE me ON e.ID_ESPECIALIDADE = me.T_ARMD_ESPECIALIDADE_ID_ESPECIALIDADE " +
                "WHERE me.T_ARMD_MEDICO_ID_PESSOA = ? AND e.IN_ATIVO = 'S'";
        List<Especialidade> especialidades = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMedico);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Especialidade especialidade = new Especialidade(rs.getString("NM_ESPECIALIDADE"));
                    especialidade.setId(rs.getInt("ID_ESPECIALIDADE"));
                    especialidade.setDescricao(rs.getString("DS_ESPECIALIDADE"));
                    especialidade.setAtivo(rs.getString("IN_ATIVO"));
                    especialidades.add(especialidade);
                }
            }

            return especialidades;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar especialidades por médico: " + e.getMessage());
        }
    }

    @Override
    public List<Medico> buscarMedicosPorEspecialidade(Integer idEspecialidade) {
        String sql = "SELECT m.T_ARMD_PESSOA_ID_PESSOA as ID_PESSOA, m.NR_CRM, m.IN_ACEITA_TELECONSULTA, " +
                "p.NM_PESSOA, p.IN_ATIVO " +
                "FROM T_ARMD_MEDICO m " +
                "INNER JOIN T_ARMD_PESSOA p ON m.T_ARMD_PESSOA_ID_PESSOA = p.ID_PESSOA " +
                "INNER JOIN T_ARMD_MEDICO_ESPECIALIDADE me ON m.T_ARMD_PESSOA_ID_PESSOA = me.T_ARMD_MEDICO_ID_PESSOA " +
                "WHERE me.T_ARMD_ESPECIALIDADE_ID_ESPECIALIDADE = ? AND p.IN_ATIVO = 'S'";
        List<Medico> medicos = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEspecialidade);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    br.com.auramed.domain.model.Pessoa pessoa = new br.com.auramed.domain.model.Pessoa(
                            rs.getString("NM_PESSOA"), null, "MEDICO"
                    );
                    pessoa.setId(rs.getInt("ID_PESSOA"));

                    Medico medico = new Medico(pessoa, rs.getString("NR_CRM"));
                    medico.setId(rs.getInt("ID_PESSOA"));
                    medico.setAceitaTeleconsulta(rs.getString("IN_ACEITA_TELECONSULTA"));
                    medicos.add(medico);
                }
            }

            return medicos;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar médicos por especialidade: " + e.getMessage());
        }
    }

    @Override
    public boolean verificarAssociacao(Integer idMedico, Integer idEspecialidade) {
        String sql = "SELECT 1 FROM T_ARMD_MEDICO_ESPECIALIDADE " +
                "WHERE T_ARMD_MEDICO_ID_PESSOA = ? AND T_ARMD_ESPECIALIDADE_ID_ESPECIALIDADE = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMedico);
            stmt.setInt(2, idEspecialidade);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao verificar associação: " + e.getMessage());
        }
    }

    @Override
    public void removerTodasEspecialidadesDoMedico(Integer idMedico) {
        String sql = "DELETE FROM T_ARMD_MEDICO_ESPECIALIDADE WHERE T_ARMD_MEDICO_ID_PESSOA = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMedico);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao remover todas especialidades do médico: " + e.getMessage());
        }
    }

    @Override
    public void removerTodosMedicosDaEspecialidade(Integer idEspecialidade) {
        String sql = "DELETE FROM T_ARMD_MEDICO_ESPECIALIDADE WHERE T_ARMD_ESPECIALIDADE_ID_ESPECIALIDADE = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEspecialidade);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao remover todos médicos da especialidade: " + e.getMessage());
        }
    }
}