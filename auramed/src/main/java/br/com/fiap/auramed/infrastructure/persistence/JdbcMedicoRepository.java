package br.com.fiap.auramed.infrastructure.persistence;

import br.com.fiap.auramed.application.repository.MedicoRepository;
import br.com.fiap.auramed.domain.model.entity.Medico;
import br.com.fiap.auramed.domain.model.vo.*;
import br.com.fiap.auramed.infrastructure.exceptions.InfraestruturaException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMedicoRepository implements MedicoRepository {

    private final DatabaseConnection databaseConnection;

    private static final String SQL_SELECT_MEDICO_COMPLETO = """
        SELECT\s
            p.id_pessoa, p.nm_pessoa, p.nm_email, p.nr_cpf, p.dt_nascimento,\s
            p.st_genero, p.nr_telefone, p.st_ativo,
            m.crm, m.nm_senha_hash,
            e.ds_logradouro, e.nr_endereco, e.ds_complemento, e.nm_bairro,\s
            e.nm_cidade, e.sg_uf, e.nr_cep
        FROM T_ARMD_PESSOA p
        INNER JOIN T_ARMD_MEDICO m ON p.id_pessoa = m.id_pessoa
        LEFT JOIN T_ARMD_ENDERECO e ON p.id_pessoa = e.id_pessoa
        WHERE p.tp_pessoa = 'MEDICO'
   \s""";

    public JdbcMedicoRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public Medico salvar(Medico medico) {
        String sqlPessoa = "INSERT INTO T_ARMD_PESSOA (id_pessoa, nm_pessoa, nm_email, nr_cpf, dt_nascimento, st_genero, nr_telefone, tp_pessoa, st_ativo) VALUES (SQ_ARMD_PESSOA.NEXTVAL, ?, ?, ?, ?, ?, ?, 'MEDICO', 'A')";
        String sqlMedico = "INSERT INTO T_ARMD_MEDICO (id_pessoa, crm, nm_senha_hash) VALUES (?, ?, ?)";
        String sqlEndereco = "INSERT INTO T_ARMD_ENDERECO (id_endereco, id_pessoa, ds_logradouro, nr_endereco, ds_complemento, nm_bairro, nm_cidade, sg_uf, nr_cep) VALUES (SQ_ARMD_ENDERECO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.databaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                Long generatedId;
                try (PreparedStatement pstmt = conn.prepareStatement(sqlPessoa, new String[]{"id_pessoa"})) {
                    pstmt.setString(1, medico.getNomeCompleto().valor());
                    pstmt.setString(2, medico.getEmail().enderecoEmail());
                    pstmt.setString(3, medico.getCpf().numero());
                    pstmt.setDate(4, Date.valueOf(medico.getDataNascimento().data()));
                    pstmt.setString(5, String.valueOf(medico.getGenero().getSigla()));
                    pstmt.setString(6, medico.getTelefone().numero());

                    int affectedRows = pstmt.executeUpdate();
                    if (affectedRows == 0) {
                        throw new SQLException("Creating person failed, no rows affected.");
                    }

                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            generatedId = rs.getLong(1);
                            medico.setId(generatedId);
                        } else {
                            throw new SQLException("Creating person failed, no ID obtained.");
                        }
                    }
                }

                try (PreparedStatement pstmt = conn.prepareStatement(sqlMedico)) {
                    pstmt.setLong(1, generatedId);
                    pstmt.setString(2, medico.getCrm().getRegistroFormatado());
                    pstmt.setString(3, medico.getSenhaHash());
                    pstmt.executeUpdate();
                }

                if (medico.getEndereco() != null) {
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlEndereco)) {
                        Endereco endereco = medico.getEndereco();
                        pstmt.setLong(1, generatedId);
                        pstmt.setString(2, endereco.logradouro());
                        pstmt.setString(3, endereco.numero());
                        pstmt.setString(4, endereco.complemento());
                        pstmt.setString(5, endereco.bairro());
                        pstmt.setString(6, endereco.cidade());
                        pstmt.setString(7, endereco.uf().name());
                        pstmt.setString(8, endereco.cep());
                        pstmt.executeUpdate();
                    }
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new InfraestruturaException("Erro ao salvar novo médico.", e);
            }
        } catch (SQLException e) {
            throw new InfraestruturaException("Erro ao obter conexão com o banco de dados.", e);
        }

        return medico;
    }

    @Override
    public Optional<Medico> buscarPorCrm(String crm) {
        String sql = STR."\{SQL_SELECT_MEDICO_COMPLETO} AND m.crm = ?";

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, crm);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToMedico(rs));
                }
            }
        } catch (SQLException e) {
            throw new InfraestruturaException(STR."Erro ao buscar médico por CRM: \{crm}", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Medico> buscarTodos() {
        List<Medico> medicos = new ArrayList<>();
        String sql = STR."\{SQL_SELECT_MEDICO_COMPLETO} AND p.st_ativo = 'A' ORDER BY p.nm_pessoa";

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                medicos.add(mapResultSetToMedico(rs));
            }
        } catch (SQLException e) {
            throw new InfraestruturaException("Erro ao buscar todos os médicos", e);
        }

        return medicos;
    }

    @Override
    public void desativar(Long id) {
        String sql = "UPDATE T_ARMD_PESSOA SET st_ativo = 'I' WHERE id_pessoa = ? AND tp_pessoa = 'MEDICO'";

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new InfraestruturaException(STR."Médico não encontrado com ID: \{id}");
            }

        } catch (SQLException e) {
            throw new InfraestruturaException(STR."Erro ao desativar médico com ID: \{id}", e);
        }
    }

    private Medico mapResultSetToMedico(ResultSet rs) throws SQLException {
        // Pessoa
        Long id = rs.getLong("id_pessoa");
        String nome = rs.getString("nm_pessoa");
        String email = rs.getString("nm_email");
        String cpf = rs.getString("nr_cpf");
        LocalDate dataNascimento = rs.getDate("dt_nascimento").toLocalDate();
        String generoStr = rs.getString("st_genero");
        String telefone = rs.getString("nr_telefone");

        // Medico
        String crmCompleto = rs.getString("crm");
        String senhaHash = rs.getString("nm_senha_hash");

        // Endereco
        Endereco endereco = null;
        String logradouro = rs.getString("ds_logradouro");
        if (logradouro != null) {
            endereco = new Endereco(
                    logradouro,
                    rs.getString("nr_endereco"),
                    rs.getString("ds_complemento"),
                    rs.getString("nm_bairro"),
                    rs.getString("nm_cidade"),
                    UF.valueOf(rs.getString("sg_uf")),
                    rs.getString("nr_cep")
            );
        }

        String crmNumero;
        UF crmUf;

        if (crmCompleto.contains("/")) {
            String[] crmParts = crmCompleto.split("/");
            crmNumero = crmParts[0];
            crmUf = UF.valueOf(crmParts[1]);
        } else if (crmCompleto.contains(" ")) {
            String[] crmParts = crmCompleto.split(" ");
            crmNumero = crmParts[1];
            String ufStr = crmParts[0].replace("CRM/", "");
            crmUf = UF.valueOf(ufStr);
        } else {
            crmNumero = crmCompleto;
            crmUf = UF.SP;
        }

        Genero genero;
        switch (generoStr) {
            case "M":
                genero = Genero.MASCULINO;
                break;
            case "F":
                genero = Genero.FEMININO;
                break;
            case "O":
                genero = Genero.OUTRO;
                break;
            default:
                throw new IllegalArgumentException("Sigla de gênero inválida: " + generoStr);
        }

        Medico medico = new Medico(
                nome, email, cpf, dataNascimento,
                genero, telefone, endereco,
                crmNumero, crmUf, senhaHash
        );

        medico.setId(id);
        return medico;
    }
}