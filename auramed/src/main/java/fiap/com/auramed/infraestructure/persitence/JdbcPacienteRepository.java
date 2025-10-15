package fiap.com.auramed.infraestructure.persitence;

import fiap.com.auramed.domain.exception.EntityNotFoundException;
import fiap.com.auramed.domain.model.*;
import fiap.com.auramed.domain.repository.MedicoRepository;
import fiap.com.auramed.domain.repository.PacienteRepository;
import fiap.com.auramed.infraestructure.exception.InfraestructureException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.*;
import java.util.Optional;

@ApplicationScoped
public class JdbcPacienteRepository implements PacienteRepository {

    private final DatabaseConnection databaseConnection;
    private final MedicoRepository medicoRepository;

    @Inject
    public JdbcPacienteRepository(DatabaseConnection databaseConnection, MedicoRepository medicoRepository) {
        this.databaseConnection = databaseConnection;
        this.medicoRepository = medicoRepository;
    }

    private Paciente mapResultSetToPaciente(ResultSet rs) throws SQLException {
        int medicoId = rs.getInt("T_ARMD_MEDICO_id_pessoa");
        Optional<Medico> medicoOptional = medicoRepository.findById(medicoId);
        Medico medico = medicoOptional.orElseThrow(() -> new EntityNotFoundException("Médico associado ao paciente não encontrado com ID: " + medicoId));
        Paciente paciente = new Paciente(
                rs.getString("nm_pessoa"),
                rs.getString("nm_email"),
                rs.getString("nr_telefone"),
                rs.getString("nr_cpf"),
                rs.getDate("dt_nascimento").toLocalDate(),
                Genero.valueOf(rs.getString("st_genero")),
                rs.getString("nr_cartao_sus"),
                medico
        );

        paciente.setId(rs.getInt("id_pessoa"));
        paciente.setStatus(StatusUsuario.valueOf(rs.getString("st_usuario")));

        return paciente;
    }

    @Override
    public Paciente save(Paciente paciente) {
        String sqlPessoa = "INSERT INTO T_ARMD_PESSOA (nm_pessoa, nm_email, nr_telefone, nr_cpf, dt_nascimento, st_genero, tp_pessoa, st_usuario) " +
                "VALUES (?, ?, ?, ?, ?, ?, 'PACIENTE', ?)";

        try (Connection conn = databaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa, new String[]{"id_pessoa"})) {
                stmtPessoa.setString(1, paciente.getNome());
                stmtPessoa.setString(2, paciente.getEmail());
                stmtPessoa.setString(3, paciente.getTelefone());
                stmtPessoa.setString(4, paciente.getCpf());
                stmtPessoa.setDate(5, Date.valueOf(paciente.getDataNascimento()));
                stmtPessoa.setString(6, paciente.getGenero().name());
                stmtPessoa.setString(7, paciente.getStatus().name());

                int affectedRows = stmtPessoa.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Falha ao inserir pessoa, nenhuma linha afetada.");
                }

                try (ResultSet generatedKeys = stmtPessoa.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        paciente.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Falha ao obter ID da pessoa inserida.");
                    }
                }
            }

            String sqlPaciente = "INSERT INTO T_ARMD_PACIENTE (T_ARMD_PESSOA_id_pessoa, nr_cartao_sus, T_ARMD_MEDICO_id_pessoa) VALUES (?, ?, ?)";
            try (PreparedStatement stmtPaciente = conn.prepareStatement(sqlPaciente)) {
                stmtPaciente.setInt(1, paciente.getId());
                stmtPaciente.setString(2, paciente.getCartaoSus());
                stmtPaciente.setInt(3, paciente.getMedico().getId());
                stmtPaciente.executeUpdate();
            }

            conn.commit();
            return paciente;

        } catch (SQLException e) {
            throw new InfraestructureException("Erro ao salvar paciente: " + e.getMessage());
        }
    }

    @Override
    public Paciente findById(Integer id) throws EntityNotFoundException {
        String sql = "SELECT p.*, pac.nr_cartao_sus, pac.T_ARMD_MEDICO_id_pessoa " +
                "FROM T_ARMD_PESSOA p " +
                "JOIN T_ARMD_PACIENTE pac ON p.id_pessoa = pac.T_ARMD_PESSOA_id_pessoa " +
                "WHERE p.id_pessoa = ? AND p.st_usuario = 'ATIVO'";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPaciente(rs);
                } else {
                    throw new EntityNotFoundException("Paciente não encontrado com ID: " + id);
                }
            }
        } catch (SQLException e) {
            throw new InfraestructureException("Erro ao buscar paciente por ID: " + e.getMessage());
        }
    }

    @Override
    public Paciente update(Paciente paciente) throws EntityNotFoundException {
        String sqlPessoa = "UPDATE T_ARMD_PESSOA SET nm_pessoa = ?, nm_email = ?, nr_telefone = ?, nr_cpf = ?, " +
                "dt_nascimento = ?, st_genero = ? WHERE id_pessoa = ?";

        try (Connection conn = databaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try(PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa)) {
                stmtPessoa.setString(1, paciente.getNome());
                stmtPessoa.setString(2, paciente.getEmail());
                stmtPessoa.setString(3, paciente.getTelefone());
                stmtPessoa.setString(4, paciente.getCpf());
                stmtPessoa.setDate(5, Date.valueOf(paciente.getDataNascimento()));
                stmtPessoa.setString(6, paciente.getGenero().name());
                stmtPessoa.setInt(7, paciente.getId());

                int affectedRows = stmtPessoa.executeUpdate();
                if (affectedRows == 0) {
                    throw new EntityNotFoundException("Paciente não encontrado com ID: " + paciente.getId());
                }
            }

            String sqlPaciente = "UPDATE T_ARMD_PACIENTE SET nr_cartao_sus = ?, T_ARMD_MEDICO_id_pessoa = ? " +
                    "WHERE T_ARMD_PESSOA_id_pessoa = ?";
            try(PreparedStatement stmtPaciente = conn.prepareStatement(sqlPaciente)) {
                stmtPaciente.setString(1, paciente.getCartaoSus());
                stmtPaciente.setInt(2, paciente.getMedico().getId());
                stmtPaciente.setInt(3, paciente.getId());
                stmtPaciente.executeUpdate();
            }

            conn.commit();
            return paciente;

        } catch (SQLException e) {
            throw new InfraestructureException("Erro ao atualizar paciente: " + e.getMessage());
        }
    }

    private void updateStatus(Integer id, StatusUsuario status) throws EntityNotFoundException {
        String sql = "UPDATE T_ARMD_PESSOA SET st_usuario = ? WHERE id_pessoa = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.name());
            stmt.setInt(2, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new EntityNotFoundException("Paciente não encontrado com ID: " + id);
            }
        } catch (SQLException e) {
            throw new InfraestructureException("Erro ao atualizar status do paciente: " + e.getMessage());
        }
    }

    @Override
    public Paciente deactivate(Integer id) throws EntityNotFoundException {
        updateStatus(id, StatusUsuario.INATIVO);
        Paciente paciente = findById(id);
        paciente.setStatus(StatusUsuario.INATIVO);
        return paciente;
    }

    @Override
    public Paciente activate(Integer id) throws EntityNotFoundException {
        updateStatus(id, StatusUsuario.ATIVO);
        return findById(id);
    }
}