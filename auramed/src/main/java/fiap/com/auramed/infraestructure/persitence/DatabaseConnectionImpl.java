package fiap.com.auramed.infraestructure.persitence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@ApplicationScoped
public class DatabaseConnectionImpl implements DatabaseConnection {

    private final DataSource dataSource;

    @Inject
    public DatabaseConnectionImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }
}
