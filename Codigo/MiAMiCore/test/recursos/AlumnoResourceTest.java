/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import java.util.List;
import modelo.Alumnos;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author macbookpro
 */
public class AlumnoResourceTest {
    
    public AlumnoResourceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of registrarAlumno method, of class AlumnoResource.
     */
    @Test
    public void testRegistrarAlumno() {
        System.out.println("registrarAlumno");
        Alumnos alumno = null;
        AlumnoResource instance = new AlumnoResource();
        boolean expResult = false;
        boolean result = instance.registrarAlumno(alumno);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminarAlumno method, of class AlumnoResource.
     */
    @Test
    public void testEliminarAlumno() throws Exception {
        System.out.println("eliminarAlumno");
        Alumnos alumno = null;
        AlumnoResource instance = new AlumnoResource();
        boolean expResult = false;
        boolean result = instance.eliminarAlumno(alumno);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of modificarAlumno method, of class AlumnoResource.
     */
    @Test
    public void testModificarAlumno() throws Exception {
        System.out.println("modificarAlumno");
        Alumnos alumno = null;
        AlumnoResource instance = new AlumnoResource();
        boolean expResult = false;
        boolean result = instance.modificarAlumno(alumno);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of visualizarRegistros method, of class AlumnoResource.
     */
    @Test
    public void testVisualizarRegistros() {
        System.out.println("visualizarRegistros");
        AlumnoResource instance = new AlumnoResource();
        List<Alumnos> expResult = null;
        List<Alumnos> result = instance.visualizarRegistros();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
