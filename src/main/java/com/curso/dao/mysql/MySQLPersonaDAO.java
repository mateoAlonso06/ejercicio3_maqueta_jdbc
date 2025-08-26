package com.curso.dao.mysql;

import com.curso.dao.PersonaDAO;
import com.curso.exception.DAOException;
import com.curso.model.Persona;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLPersonaDAO implements PersonaDAO {
    final String INSERT = "INSERT INTO personas (nombre, edad) VALUES (?, ?)";
    final String UPDATE = "UPDATE personas SET nombre = ?, edad = ? WHERE id = ?";
    final String DELETE = "DELETE FROM personas WHERE id = ?";
    final String GETALL = "SELECT * FROM personas LIMIT 10";
    final String GETBYID = "SELECT * FROM personas WHERE id = ?";

    private final Connection con;

    public MySQLPersonaDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void save(Persona a) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet keys = null;
        try {
            ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, a.getNombre());
            ps.setInt(2, a.getEdad());

            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new DAOException("Error al insertar el elemento", null);
            }
            // Leer la PK autogenerada
            keys = ps.getGeneratedKeys();
            if (keys.next()) {
                long id = keys.getLong(1);
                a.setId(id);
            } else {
                throw new DAOException("No se obtuvo id generado", null);
            }
            System.out.println("Se ha agregado con exito la persona a la base de datos");
        } catch (SQLException e) {
            throw new DAOException("Error en SQL", e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Error cerrando PreparedStatement", e);
            }
        }
    }

    private Persona convertir(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String nombre = rs.getString("nombre");
        int edad = rs.getInt("edad");
        return new Persona(id, nombre, edad);
    }

    @Override
    public void update(Persona a) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(UPDATE);
            // tengo que actualizar todos los campos
            ps.setString(1, a.getNombre());
            ps.setInt(2, a.getEdad());
            ps.setLong(3, a.getId());
            ps.executeUpdate();
            System.out.println("Se actualizaron con exitos los campos");
        } catch (SQLException e) {
            throw new DAOException("Error en SQL", e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Error cerrando PreparedStatement", e);
            }
        }
    }

    @Override
    public void delete(Persona a) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(DELETE);
            ps.setLong(1, a.getId());
            ps.executeUpdate();
            System.out.println("La persona con id: " + a.getId() + " fue eliminada con exito");
        } catch (SQLException e) {
            throw new DAOException("Error en SQL", e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Error cerrando PreparedStament", e);
            }
        }
    }

    @Override
    public List<Persona> getAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Persona> personas = new ArrayList<>();

        try {
            ps = con.prepareStatement(GETALL);
            rs = ps.executeQuery();

            while (rs.next()) {
                personas.add(
                        convertir(rs)
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Error en SQL", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException("Error cerrando ResultSet", e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException("Error cerrando PreparedStatement", e);
                }
            }
        }
        return personas;
    }

    @Override
    public Persona getById(Long id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(GETBYID);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return convertir(rs);
            } else {
                throw new DAOException("No se ha encontrado el registro con id " + id);
            }
        } catch (SQLException e) {
            throw new DAOException("Error en SQL", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException("Error cerrando ResultSet", e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException("Error cerrando PreparedStatement", e);
                }
            }
        }
    }
}
