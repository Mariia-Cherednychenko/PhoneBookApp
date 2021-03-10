package database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseConnection {
   private String dsn = "jdbc:postgresql://localhost:5432/contacts";
   private String user = "postgres";
   private String password = "0000";
   private HikariConfig config = new HikariConfig();
   DataSource dataSource;
   PreparedStatement preparedStatement;
   Connection connection;

    public DataBaseConnection()  {
        config.setJdbcUrl(dsn);
        config.setUsername(user);
        config.setPassword(password);
        dataSource = new HikariDataSource(config);
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
