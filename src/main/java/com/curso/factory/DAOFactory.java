package com.curso.factory;

import com.curso.dao.PersonaDAO;
import com.curso.dao.mysql.MySQLPersonaDAO;
import com.curso.factory.mysql.MySQLDAOFactory;
import com.curso.model.DBType;

import java.sql.Connection;

public abstract class DAOFactory {
    // Es un metodo por cada DAO de mi familia
    public abstract PersonaDAO getPersonaDAO(Connection c);

    public static DAOFactory of(DBType type) {
        return switch(type) {
            // USO SINGLETON EN LOS DAOFACTORY HIJOS
            case MYSQL -> MySQLDAOFactory.getInstance();
            default -> throw new IllegalArgumentException("DBType no soportado: " + type);
        };
    }
}
