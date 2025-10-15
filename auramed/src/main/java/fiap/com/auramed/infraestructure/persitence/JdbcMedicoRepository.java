package fiap.com.auramed.infraestructure.persitence;
import fiap.com.auramed.domain.model.Genero;
import fiap.com.auramed.domain.model.Medico;
import fiap.com.auramed.domain.model.StatusUsuario;
import fiap.com.auramed.domain.repository.MedicoRepository;
import fiap.com.auramed.infraestructure.exception.InfraestructureException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@ApplicationScoped
public class JdbcMedicoRepository implements MedicoRepository {

    private final DatabaseConnection databaseConnection;

    @Inject
    public JdbcMedicoRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    private Medico mapResultSetToMedico(ResultSet rs) throws SQLException {
        Medico medico = new Medico(
                rs.getString("nm_pessoa"),
                rs.getString("nm_email"),
                rs.getString("nr_telefone"),
                rs.getString("nr_cpf"),
                rs.getDate("dt_nascimento").toLocalDate(),
                Genero.valueOf(rs.getString("st_genero")),
                rs.getString("nr_crm"),
                null,
                rs.getString("nm_senha_hash")
        );
        medico.setId(rs.getInt("id_pessoa"));
        medico.setStatus(StatusUsuario.valueOf(rs.getString("st_usuario")));
        return medico;
    }

    @Override
    public Optional<Medico> findById(Integer id) {
        String sql = "SELECT p.*, m.nr_crm, m.nm_senha_hash " +
                "FROM T_ARMD_PESSOA p " +
                "JOIN T_ARMD_MEDICO m ON p.id_pessoa = m.T_ARMD_PESSOA_id_pessoa " +
                "WHERE p.id_pessoa = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToMedico(rs));
                }
            }
        } catch (SQLException e) {
            throw new InfraestructureException("Erro ao buscar médico por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Medico> findByEmail(String email) {
        String sql = "SELECT p.*, m.nr_crm, m.nm_senha_hash " +
                "FROM T_ARMD_PESSOA p " +
                "JOIN T_ARMD_MEDICO m ON p.id_pessoa = m.T_ARMD_PESSOA_id_pessoa " +
                "WHERE p.nm_email = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToMedico(rs));
                }
            }
        } catch (SQLException e) {
            throw new InfraestructureException("Erro ao buscar médico por email: " + e.getMessage());
        }
        return Optional.empty();
    }
}