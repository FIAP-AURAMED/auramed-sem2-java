package br.com.fiap.auramed.infrastructure.config;

import br.com.fiap.auramed.application.repository.MedicoRepository;
import br.com.fiap.auramed.infrastructure.persistence.DatabaseConnection;
import br.com.fiap.auramed.infrastructure.persistence.DatabaseConnectionImpl;
import br.com.fiap.auramed.infrastructure.persistence.JdbcMedicoRepository;
import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DatabaseConfig {

    @ApplicationScoped
    public DatabaseConnection databaseConnection(AgroalDataSource dataSource) {
        return new DatabaseConnectionImpl(dataSource);
    }

    @ApplicationScoped
    public MedicoRepository medicoRepository(DatabaseConnection databaseConnection) {
        return new JdbcMedicoRepository(databaseConnection);
    }
}
