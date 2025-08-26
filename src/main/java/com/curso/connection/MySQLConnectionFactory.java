package com.curso.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionFactory implements ConnectionFactory {

    @Override
    public Connection get() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/practico?serverTimezone=UTC";
        String user = "mateo";
        String password = "1234";
        return DriverManager.getConnection(url, user, password);
    }
}
