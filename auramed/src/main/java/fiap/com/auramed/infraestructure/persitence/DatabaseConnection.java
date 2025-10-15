package fiap.com.auramed.infraestructure.persitence;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnection {
    Connection getConnection() throws SQLException;
}
