/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Test;
import recursos.AlumnoResource;
import modelo.Alumno;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author AndrésRoberto
 */
public class AlumnoJpaControllerExtTest {
    EntityManagerFactory emf;

    public AlumnoJpaControllerExtTest() {
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
    }
    @Test
    public void testGetAllAlumnos(){
        System.out.println("Obtener todos los alumnos, lista con alumnos");
        AlumnoJpaControllerExtended instancia = new AlumnoJpaControllerExtended(emf);
        AlumnoResource recurso = new AlumnoResource();
        List<Alumno> espResultado = recurso.visualizarRegistros();
        List<Alumno> resultado = instancia.getAllAlumnos();
        assertEquals(espResultado, resultado);
    }
    
    @Test
    public void testGetAllAlumnosVacio(){
        System.out.println("Obtener todos los alumnos, lista con alumnos");
        AlumnoJpaControllerExtended instancia = new AlumnoJpaControllerExtended(emf);
        AlumnoResource recurso = new AlumnoResource();
        List<Alumno> espResultado = recurso.visualizarRegistros();
        List<Alumno> resultado = instancia.getAllAlumnos();
        assertEquals(espResultado, resultado);
    }
    
    
    
    
    
}
