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
}