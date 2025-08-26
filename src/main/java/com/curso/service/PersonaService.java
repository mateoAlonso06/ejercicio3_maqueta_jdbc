package com.curso.service;

import com.curso.connection.ConnectionFactory;
import com.curso.dao.PersonaDAO;
import com.curso.factory.DAOFactory;
import com.curso.model.Persona;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonaService {
    private final DAOFactory factory;
    private final ConnectionFactory connFact;

    // connection factory trae la información necesaria con los datos de la DB (host, user, password)
    public PersonaService(DAOFactory factory, ConnectionFactory connFact) {
        this.factory = factory;
        this.connFact = connFact;
    }

    public void crearPersona(Persona p) {
        Connection conn = null;
        boolean ok = false;
        try {
            conn = connFact.get();
            conn.setAutoCommit(false);

            PersonaDAO dao = factory.getPersonaDAO(conn);
            dao.save(p);

            conn.commit();
            ok = true;
        } catch (SQLException e) {
            throw new RuntimeException("Error de conexion/transaccion", e);
        } finally {
            if (conn != null) {
                try {
                    if (!ok) conn.rollback();
                } catch (SQLException ignore) {
                }
                try {
                    conn.close();
                } catch (SQLException ignore) {
                }
            }
        }
    }

    public List<Persona> getPersonas() {
        Connection conn = null;
        List<Persona> personas = new ArrayList<>();
        try {
            conn = connFact.get();
            PersonaDAO dao = factory.getPersonaDAO(conn);
            personas = dao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la lista de personas", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return personas;
    }

    public boolean delete(Persona a) {
        Connection conn = null;
        boolean ok = false;
        try {
            conn = connFact.get();
            PersonaDAO dao = factory.getPersonaDAO(conn);
            dao.delete(a);
            ok = true;
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar", e);
        }
        return ok;
    }

    public Persona getPersonaById(Long id) {
        Connection conn = null;
        try {
            conn = connFact.get();
            PersonaDAO dao = factory.getPersonaDAO(conn);
            return dao.getById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error leyendo persona", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignore) {
                }
            }
        }
    }
}
