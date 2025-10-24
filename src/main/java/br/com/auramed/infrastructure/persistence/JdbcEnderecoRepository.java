package br.com.auramed.infrastructure.persistence;

import br.com.auramed.domain.exception.EntidadeNaoLocalizadaException;
import br.com.auramed.domain.model.Endereco;
import br.com.auramed.domain.model.Pessoa;
import br.com.auramed.domain.repository.EnderecoRepository;
import br.com.auramed.infrastructure.exception.InfrastructureException;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcEnderecoRepository implements EnderecoRepository {

    private final DatabaseConnection databaseConnection;

    @Inject
    public JdbcEnderecoRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    private Endereco mapResultSetToEndereco(ResultSet rs) throws SQLException {
        Pessoa pessoa = new Pessoa(null, null, null);
        pessoa.setId(rs.getInt("T_ARMD_PESSOA_id_pessoa"));

        Endereco endereco = new Endereco(
                pessoa,
                rs.getString("DS_LOGRADOURO"),
                rs.getString("NR_ENDERECO"),
                rs.getString("NM_BAIRRO"),
                rs.getString("NM_CIDADE"),
                rs.getString("SG_UF"),
                rs.getString("NR_CEP")
        );
        endereco.setId(rs.getInt("ID_ENDERECO"));
        endereco.setTipoEndereco(rs.getString("TP_ENDERECO"));
        endereco.setComplemento(rs.getString("DS_COMPLEMENTO"));
        endereco.setPrincipal(rs.getString("IN_PRINCIPAL"));

        return endereco;
    }

    @Override
    public Endereco buscarPorId(Integer id) throws EntidadeNaoLocalizadaException {
        String sql = "SELECT * FROM T_ARMD_ENDERECO WHERE ID_ENDERECO = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEndereco(rs);
                } else {
                    throw new EntidadeNaoLocalizadaException("Endereço não encontrado com ID: " + id);
                }
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar endereço por ID: " + e.getMessage());
        }
    }

    @Override
    public List<Endereco> buscarPorPessoaId(Integer pessoaId) {
        String sql = "SELECT * FROM T_ARMD_ENDERECO WHERE T_ARMD_PESSOA_id_pessoa = ?";
        List<Endereco> enderecos = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pessoaId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    enderecos.add(mapResultSetToEndereco(rs));
                }
            }

            return enderecos;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar endereços por pessoa ID: " + e.getMessage());
        }
    }

    @Override
    public List<Endereco> buscarEnderecosPrincipais() {
        String sql = "SELECT * FROM T_ARMD_ENDERECO WHERE IN_PRINCIPAL = 'S'";
        List<Endereco> enderecos = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                enderecos.add(mapResultSetToEndereco(rs));
            }

            return enderecos;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar endereços principais: " + e.getMessage());
        }
    }

    @Override
    public List<Endereco> buscarTodos() {
        String sql = "SELECT * FROM T_ARMD_ENDERECO";
        List<Endereco> enderecos = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                enderecos.add(mapResultSetToEndereco(rs));
            }

            return enderecos;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao buscar todos endereços: " + e.getMessage());
        }
    }

    @Override
    public Endereco salvar(Endereco endereco) {
        String sql = "INSERT INTO T_ARMD_ENDERECO (T_ARMD_PESSOA_id_pessoa, TP_ENDERECO, DS_LOGRADOURO, " +
                "NR_ENDERECO, DS_COMPLEMENTO, NM_BAIRRO, NM_CIDADE, SG_UF, NR_CEP, IN_PRINCIPAL) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_ENDERECO"})) {

            stmt.setInt(1, endereco.getPessoa().getId());
            stmt.setString(2, endereco.getTipoEndereco());
            stmt.setString(3, endereco.getLogradouro());
            stmt.setString(4, endereco.getNumero());
            stmt.setString(5, endereco.getComplemento());
            stmt.setString(6, endereco.getBairro());
            stmt.setString(7, endereco.getCidade());
            stmt.setString(8, endereco.getUf());
            stmt.setString(9, endereco.getCep());
            stmt.setString(10, endereco.getPrincipal());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new InfrastructureException("Nenhuma linha afetada ao salvar endereço.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    endereco.setId(generatedKeys.getInt(1));
                }
            }
            return endereco;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao salvar endereço: " + e.getMessage());
        }
    }

    @Override
    public Endereco editar(Endereco endereco) throws EntidadeNaoLocalizadaException {
        if (endereco.getId() == null) {
            throw new IllegalArgumentException("ID do endereço não pode ser nulo para edição");
        }

        String sql = "UPDATE T_ARMD_ENDERECO SET TP_ENDERECO = ?, DS_LOGRADOURO = ?, NR_ENDERECO = ?, " +
                "DS_COMPLEMENTO = ?, NM_BAIRRO = ?, NM_CIDADE = ?, SG_UF = ?, NR_CEP = ?, IN_PRINCIPAL = ? " +
                "WHERE ID_ENDERECO = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, endereco.getTipoEndereco());
            stmt.setString(2, endereco.getLogradouro());
            stmt.setString(3, endereco.getNumero());
            stmt.setString(4, endereco.getComplemento());
            stmt.setString(5, endereco.getBairro());
            stmt.setString(6, endereco.getCidade());
            stmt.setString(7, endereco.getUf());
            stmt.setString(8, endereco.getCep());
            stmt.setString(9, endereco.getPrincipal());
            stmt.setInt(10, endereco.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new EntidadeNaoLocalizadaException("Endereço não encontrado com ID: " + endereco.getId());
            }

            return endereco;

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao editar endereço: " + e.getMessage());
        }
    }

    @Override
    public void remover(Integer id) throws EntidadeNaoLocalizadaException {
        String sql = "DELETE FROM T_ARMD_ENDERECO WHERE ID_ENDERECO = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new EntidadeNaoLocalizadaException("Endereço não encontrado com ID: " + id);
            }

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao remover endereço: " + e.getMessage());
        }
    }

    @Override
    public void removerTodosPorPessoaId(Integer pessoaId) throws EntidadeNaoLocalizadaException {
        String sql = "DELETE FROM T_ARMD_ENDERECO WHERE T_ARMD_PESSOA_id_pessoa = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pessoaId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new InfrastructureException("Erro ao remover endereços por pessoa ID: " + e.getMessage());
        }
    }
}