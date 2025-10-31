package br.com.auramed.infrastructure.config;

import br.com.auramed.domain.repository.*;
import br.com.auramed.infrastructure.persistence.*;
import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class DatabaseConfig {

    @Produces
    @ApplicationScoped
    public DatabaseConnection databaseConnection(AgroalDataSource dataSource) {
        return new DatabaseConnectionImpl(dataSource);
    }

    @Produces
    @ApplicationScoped
    public PessoaRepository pessoaRepository(DatabaseConnection databaseConnection) {
        return new JdbcPessoaRepository(databaseConnection);
    }

    @Produces
    @ApplicationScoped
    public MedicoRepository medicoRepository(DatabaseConnection databaseConnection) {
        return new JdbcMedicoRepository(databaseConnection);
    }

    @Produces
    @ApplicationScoped
    public AuthMedicoRepository authMedicoRepository(DatabaseConnection databaseConnection) {
        return new JdbcAuthMedicoRepository(databaseConnection);
    }

    @Produces
    @ApplicationScoped
    public EspecialidadeRepository especialidadeRepository(DatabaseConnection databaseConnection) {
        return new JdbcEspecialidadeRepository(databaseConnection);
    }

    @Produces
    @ApplicationScoped
    public EnderecoRepository enderecoRepository(DatabaseConnection databaseConnection) {
        return new JdbcEnderecoRepository(databaseConnection);
    }

    @Produces
    @ApplicationScoped
    public MedicoEspecialidadeRepository medicoEspecialidadeRepository(DatabaseConnection databaseConnection) {
        return new JdbcMedicoEspecialidadeRepository(databaseConnection);
    }

    @Produces
    @ApplicationScoped
    public PacienteRepository pacienteRepository(DatabaseConnection databaseConnection) {
        return new JdbcPacienteRepository(databaseConnection);
    }

    @Produces
    @ApplicationScoped
    public CuidadorRepository cuidadorRepository(DatabaseConnection databaseConnection) {
        return new JdbcCuidadorRepository(databaseConnection);
    }

    @Produces
    @ApplicationScoped
    public PacienteCuidadorRepository pacienteCuidadorRepository(DatabaseConnection databaseConnection) {
        return new JdbcPacienteCuidadorRepository(databaseConnection);
    }

    @Produces
    @ApplicationScoped
    public InfoTeleconsultaRepository infoTeleconsultaRepository(DatabaseConnection databaseConnection) {
        return new JdbcInfoTeleconsultaRepository(databaseConnection);
    }

    @Produces
    @ApplicationScoped
    public PerfilCognitivoRepository perfilCognitivoRepository(DatabaseConnection databaseConnection) {
        return new JdbcPerfilCognitivoRepository(databaseConnection);
    }

    @Produces
    @ApplicationScoped
    public ConversacaoRepository conversacaoRepository(DatabaseConnection databaseConnection) {
        return new JdbcConversacaoRepository(databaseConnection);
    }

    @Produces
    @ApplicationScoped
    public BaseConhecimentoRepository baseConhecimentoRepository(DatabaseConnection databaseConnection) {
        return new JdbcBaseConhecimentoRepository(databaseConnection);
    }
}