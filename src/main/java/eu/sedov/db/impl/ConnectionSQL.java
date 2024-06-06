package eu.sedov.db.impl;

import eu.sedov.db.ConnectionManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionSQL implements ConnectionManager {
    private final String url;
    private final String username;
    private final String password;

    public ConnectionSQL() throws IOException{
        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("database.properties"))){
            props.load(in);
        }
        url = props.getProperty("url");
        username = props.getProperty("username");
        password = props.getProperty("password");
    }

    public ConnectionSQL(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                url,
                username,
                password
        );
    }
}
