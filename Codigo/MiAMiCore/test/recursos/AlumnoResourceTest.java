/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import controladores.AlumnoJpaControllerExtended;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
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
    //@Test
    public void testEliminarAlumno() throws Exception {
        System.out.println("eliminarAlumno");
        AlumnoResource instance = new AlumnoResource();
        boolean expResult = true;
        
        //boolean result = instance.eliminarAlumno(instance.buscarAlumnoPorNombre(test.getNombre()));
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of modificarAlumno method, of class AlumnoResource.
     */
    //@Test
    public void testModificarAlumno() throws Exception {
        System.out.println("modificarAlumno");
        AlumnoResource instance = new AlumnoResource();
        List<Alumno> alumno = instance.buscarAlumnoPorNombre(test.getNombre());
        //alumno.setNombre("test2");
        boolean expResult = true;
        //boolean result = instance.modificarAlumno(alumno);
        //assertEquals(expResult, result);
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
        
    }

    //@Test
    public void testBuscarAlumnoPorNombre() {
    }
    
}
