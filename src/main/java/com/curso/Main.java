package com.curso;

import com.curso.connection.ConnectionFactory;
import com.curso.connection.MySQLConnectionFactory;
import com.curso.factory.DAOFactory;
import com.curso.factory.mysql.MySQLDAOFactory;
import com.curso.model.Persona;
import com.curso.service.PersonaService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DAOFactory factory = MySQLDAOFactory.getInstance();
        ConnectionFactory connection = new MySQLConnectionFactory();

        PersonaService service = new PersonaService(factory, connection);

        Persona user1 = new Persona("Mateo", 23);
        Persona user2 = new Persona("Ulises", 22);
        Persona user3 = new Persona("Lara", 23);
        Persona user4 = new Persona("Nahuel", 20);

        service.crearPersona(user1);
        service.crearPersona(user2);
        service.crearPersona(user3);
        service.crearPersona(user4);

        System.out.println("Personas almacenadas en la base de datos");
        service.getPersonas().forEach(System.out::println);

        Long id = 4L;
        System.out.println("\nPersona con id: " + id);

        Persona p = service.getPersonaById(id);
        System.out.println(p);

        System.out.println("\nEliminar datos de persona con id: " + id);
        service.delete(user4);

        System.out.println("\nPersonas almacenadas en la base de datos");
        service.getPersonas().forEach(System.out::println);
    }
}
