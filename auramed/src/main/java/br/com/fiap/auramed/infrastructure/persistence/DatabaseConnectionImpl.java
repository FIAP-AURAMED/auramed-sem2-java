package br.com.fiap.auramed.infrastructure.persistence;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionImpl implements DatabaseConnection {
    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }
}
