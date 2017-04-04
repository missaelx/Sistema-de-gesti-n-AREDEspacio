/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Alumno;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author macbookpro
 */
public class AlumnoJpaControllerExtendedTest {
    EntityManagerFactory emf;
    public AlumnoJpaControllerExtendedTest() {
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
    }

    @Test
    public void testGetAlumnoFromNombre() {
        System.out.println("getAlumnoFromNombre");
        String nombre = "";
        AlumnoJpaControllerExtended instance = new AlumnoJpaControllerExtended(emf);
        List<Alumno> result = instance.getAlumnoFromNombre(nombre);
        assertNotNull(result);
    }

    @Test
    public void testGetAlumnoFromCorreo() {
        System.out.println("getAlumnoFromCorreo");
        String correo = "";
        AlumnoJpaControllerExtended instance = new AlumnoJpaControllerExtended(emf);
        List<Alumno> result = instance.getAlumnoFromCorreo(correo);
        assertNotNull(result);
    }

    @Test
    public void testGetAllAlumnos() {
        System.out.println("getAllAlumnos");
        AlumnoJpaControllerExtended instance = new AlumnoJpaControllerExtended(emf);
        List<Alumno> result = instance.getAllAlumnos();
        assertNotNull( result);
        
    }
    
}
