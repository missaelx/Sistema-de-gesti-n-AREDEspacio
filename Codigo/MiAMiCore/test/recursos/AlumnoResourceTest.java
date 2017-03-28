/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import controladores.AlumnoJpaController;
import controladores.AlumnoJpaControllerExtended;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Alumno;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author macbookpro
 */
public class AlumnoResourceTest {
    private Alumno test;
    EntityManagerFactory emf;
    
    public AlumnoResourceTest(){
        test = new Alumno();
        test.setActivo(true);
        test.setApellidos("test");
        test.setCorreo("test");
        test.setDiapago(new Date());
        test.setFechaNacimiento(new Date());
        test.setNombre("test");
        test.setTelefono("test");
        test.setTelefonoEmergencia("test");
        test.setTipoSangre("O+");
        test.setId(1);
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
    }

    
    

    /**
     * Test of registrarAlumno method, of class AlumnoResource.
     * Con alumno correcto
     */
    @Test
    public void testRegistrarAlumno() {
        System.out.println("registrarAlumno");
        AlumnoResource instance = new AlumnoResource();
        boolean expResult = true;
        boolean result = instance.registrarAlumno(test);
        assertEquals(expResult, result);
    }

    /**
     * Test of eliminarAlumno method, of class AlumnoResource.
     */
    @Test
    public void testEliminarAlumno() throws Exception {
        System.out.println("eliminarAlumno");
        AlumnoResource instance = new AlumnoResource();
        boolean expResult = true;
        
        AlumnoJpaController alumnosController = new AlumnoJpaController(emf);
        
        boolean result = instance.eliminarAlumno(alumnosController.findAlumno(1));
        assertEquals(expResult, result);
        
    }

    /**
     * Test of modificarAlumno method, of class AlumnoResource.
     */
    @Test
    public void testModificarAlumno() throws Exception {
        System.out.println("modificarAlumno");
        AlumnoResource instance = new AlumnoResource();
        
        AlumnoJpaController alumnosController = new AlumnoJpaController(emf);
        
        Alumno editado = alumnosController.findAlumno(1);
        editado.setNombre("Editado en prueba");
        instance.modificarAlumno(editado);
        boolean expResult = true;
        assertEquals(expResult, true);
    }

    /**
     * Test of visualizarRegistros method, of class AlumnoResource.
     */
    @Test
    public void testVisualizarRegistros() {
        System.out.println("visualizarRegistros");
        AlumnoResource instance = new AlumnoResource();
        List<Alumno> result = instance.visualizarRegistros();
        assertNotNull(result);
    }

    @Test
    public void testGetAlumnoPorId() {
        System.out.println("testGetAlumnoPorId");
        AlumnoResource instance = new AlumnoResource();
        AlumnoJpaController alumnosController = new AlumnoJpaController(emf);
        Alumno aFromRecurso = instance.getAlumnoPorId(1);
        Alumno aFromJpa = alumnosController.findAlumno(1);
        assertEquals(aFromJpa, aFromRecurso);
        
    }

    @Test
    public void testBuscarAlumnoPorNombre() {
        AlumnoResource instance = new AlumnoResource();
        List<Alumno> lista = instance.buscarAlumnoPorNombre("t");
        for(Alumno a: lista){
            System.out.println(a.toString());
        }
        assertNotNull(lista);
    }
    
}
