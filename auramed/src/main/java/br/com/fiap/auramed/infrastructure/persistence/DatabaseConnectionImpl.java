package br.com.fiap.auramed.infrastructure.persistence;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionImpl implements DatabaseConnection {

    private final DataSource dataSource;

    public DatabaseConnectionImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}