package com.curso.factory.mysql;

import com.curso.dao.PersonaDAO;
import com.curso.dao.mysql.MySQLPersonaDAO;
import com.curso.factory.DAOFactory;

import java.sql.Connection;

public class MySQLDAOFactory extends DAOFactory {
    private static final MySQLDAOFactory INSTANCE = new MySQLDAOFactory();

    private MySQLDAOFactory() {}

    public static MySQLDAOFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public PersonaDAO getPersonaDAO(Connection c) {
        return new MySQLPersonaDAO(c);
    }
}
