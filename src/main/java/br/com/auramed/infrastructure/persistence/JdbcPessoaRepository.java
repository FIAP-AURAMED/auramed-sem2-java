package br.com.auramed.infrastructure.persistence;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.Pessoa;
import br.com.auramed.domain.repository.PessoaRepository;
import br.com.auramed.infrastructure.exception.InfrastructureException;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcPessoaRepository implements PessoaRepository {

    private final DatabaseConnection databaseConnection;

    @Inject
    public JdbcPessoaRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    private Pessoa mapResultSetToPessoa(ResultSet rs) throws SQLException {
        Pessoa pessoa = new Pessoa(
                rs.getString("NM_PESSOA"),
                rs.getString("NR_TELEFONE"),
                rs.getString("TP_PESSOA")
        );
        pessoa.setId(rs.getInt("ID_PESSOA"));
        pessoa.setEmail(rs.getString("NM_EMAIL"));
        pessoa.setCpf(rs.getString("NR_CPF"));

        Date dataNascimento = rs.getDate("DT_NASCIMENTO");
        if (dataNascimento != null) {
            pessoa.setDataNascimento(dataNascimento.toLocalDate());
        }

        pessoa.setGenero(rs.getString("ST_GENERO"));
        pessoa.setDataCadastro(rs.getTimestamp("DT_CADASTRO").toLocalDateTime());
        pessoa.setAtivo(rs.getString("IN_ATIVO"));

        return pessoa;
    }

    @Override
    public Pessoa buscarPorId(Integer id) throws EntidadeNaoLocalizadaException {
        String sql = "SELECT * FROM T_ARMD_PESSOA WHERE ID_PESSOA = ? AND IN_ATIVO = 'S'";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPessoa(rs);
                } else {
                    throw new EntidadeNaoLocalizadaException("Pessoa não encontrada com ID: " + id);
                }
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar pessoa por ID: " + e.getMessage());
        }
    }

    @Override
    public Pessoa buscarPorEmail(String email) throws EntidadeNaoLocalizadaException {
        String sql = "SELECT * FROM T_ARMD_PESSOA WHERE NM_EMAIL = ? AND IN_ATIVO = 'S'";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPessoa(rs);
                } else {
                    throw new EntidadeNaoLocalizadaException("Pessoa não encontrada com email: " + email);
                }
            }
        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar pessoa por email: " + e.getMessage());
        }
    }

    @Override
    public Pessoa buscarPorCpf(String cpf) throws EntidadeNaoLocalizadaException {
        String sql = "SELECT * FROM T_ARMD_PESSOA WHERE NR_CPF = ? AND IN_ATIVO = 'S'";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPessoa(rs);
                } else {
                    throw new EntidadeNaoLocalizadaException("Pessoa não encontrada com CPF: " + cpf);
                }
            }
        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar pessoa por CPF: " + e.getMessage());
        }
    }

    @Override
    public List<Pessoa> buscarTodos() {
        String sql = "SELECT * FROM T_ARMD_PESSOA WHERE IN_ATIVO = 'S'";
        List<Pessoa> pessoas = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pessoas.add(mapResultSetToPessoa(rs));
            }

            return pessoas;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar todas pessoas: " + e.getMessage());
        }
    }

    @Override
    public List<Pessoa> buscarPorTipo(String tipoPessoa) {
        String sql = "SELECT * FROM T_ARMD_PESSOA WHERE TP_PESSOA = ? AND IN_ATIVO = 'S'";
        List<Pessoa> pessoas = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tipoPessoa);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pessoas.add(mapResultSetToPessoa(rs));
                }
            }

            return pessoas;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar pessoas por tipo: " + e.getMessage());
        }
    }

    @Override
    public List<Pessoa> buscarAtivos() {
        String sql = "SELECT * FROM T_ARMD_PESSOA WHERE IN_ATIVO = 'S'";
        List<Pessoa> pessoas = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pessoas.add(mapResultSetToPessoa(rs));
            }

            return pessoas;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar pessoas ativas: " + e.getMessage());
        }
    }

    @Override
    public Pessoa salvar(Pessoa pessoa) {
        String sql = "INSERT INTO T_ARMD_PESSOA (NM_PESSOA, NM_EMAIL, NR_CPF, DT_NASCIMENTO, ST_GENERO, NR_TELEFONE, TP_PESSOA, DT_CADASTRO, IN_ATIVO) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_PESSOA"})) {

            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getEmail());
            stmt.setString(3, pessoa.getCpf());

            if (pessoa.getDataNascimento() != null) {
                stmt.setDate(4, Date.valueOf(pessoa.getDataNascimento()));
            } else {
                stmt.setNull(4, Types.DATE);
            }

            stmt.setString(5, pessoa.getGenero());
            stmt.setString(6, pessoa.getTelefone());
            stmt.setString(7, pessoa.getTipoPessoa());
            stmt.setString(8, pessoa.getAtivo());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new InfrastructureException("Nenhuma linha afetada ao salvar pessoa.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pessoa.setId(generatedKeys.getInt(1));
                }
            }
            return pessoa;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao salvar pessoa: " + e.getMessage());
        }
    }

    @Override
    public Pessoa editar(Pessoa pessoa) throws EntidadeNaoLocalizadaException {
        if (pessoa.getId() == null) {
            throw new IllegalArgumentException("ID da pessoa não pode ser nulo para edição");
        }

        String sql = "UPDATE T_ARMD_PESSOA SET NM_PESSOA = ?, NM_EMAIL = ?, NR_CPF = ?, DT_NASCIMENTO = ?, " +
                "ST_GENERO = ?, NR_TELEFONE = ?, TP_PESSOA = ?, IN_ATIVO = ? WHERE ID_PESSOA = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getEmail());
            stmt.setString(3, pessoa.getCpf());

            if (pessoa.getDataNascimento() != null) {
                stmt.setDate(4, Date.valueOf(pessoa.getDataNascimento()));
            } else {
                stmt.setNull(4, Types.DATE);
            }

            stmt.setString(5, pessoa.getGenero());
            stmt.setString(6, pessoa.getTelefone());
            stmt.setString(7, pessoa.getTipoPessoa());
            stmt.setString(8, pessoa.getAtivo());
            stmt.setInt(9, pessoa.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new EntidadeNaoLocalizadaException("Pessoa não encontrada com ID: " + pessoa.getId());
            }

            return pessoa;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao editar pessoa: " + e.getMessage());
        }
    }

    @Override
    public void remover(Integer id) throws EntidadeNaoLocalizadaException {
        String sql = "UPDATE T_ARMD_PESSOA SET IN_ATIVO = 'N' WHERE ID_PESSOA = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new EntidadeNaoLocalizadaException("Pessoa não encontrada com ID: " + id);
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao remover pessoa: " + e.getMessage());
        }
    }
}