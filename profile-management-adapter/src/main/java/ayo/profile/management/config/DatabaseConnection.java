package ayo.profile.management.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class DatabaseConnection {

    @Value("${spring.datasource.username}")
    private static String userName;
    @Value("${spring.datasource.password}")
    private static String password;
    @Value("${spring.datasource.url}")
    private static String url;
    @Value("${spring.datasource.driverClassName}")
    private static String driverClassName;
    private static String dbms = "h2";
    private static String dbName = "ayodb";

    public static Connection getConnection() {
        Connection connection = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        try{
            if (dbms.equals("h2")) {
                connection = DriverManager.getConnection(url, connectionProps);
            } else if (dbms.equals("ayodb")) {
                connection = DriverManager.getConnection("jdbc:" + dbms + ":" + dbName + ";create=true", connectionProps);
            }
        }catch (SQLException e){
            e.getSQLState();
        }
        return connection;
    }
}
