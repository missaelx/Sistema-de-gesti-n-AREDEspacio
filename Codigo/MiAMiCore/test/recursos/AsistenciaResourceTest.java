/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Alumno;
import modelo.Asistencia;
import modelo.GrupoClase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miguel Acosta
 */
public class AsistenciaResourceTest {
    private Asistencia test;
    EntityManagerFactory emf;
    
    public AsistenciaResourceTest(){
        test = new Asistencia();
        test.setDia(new Date());
        test.setId(Integer.SIZE);
        test.setIdAlumno(new Alumno());
        test.setIdGrupoClase(new GrupoClase());
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
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
     * Test of registrarAsistencia method, of class AsistenciaResource.
     */
    @Test
    public void testRegistrarAsistencia() {
        System.out.println("registrarAsistencia");
        Asistencia asistencia = test;
        
        AsistenciaResource instance = new AsistenciaResource();
        boolean expResult = true;
        boolean result = instance.registrarAsistencia(asistencia);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarAsistenciasAlumno method, of class AsistenciaResource.
     */
    @Test
    public void testBuscarAsistenciasAlumno() {
        System.out.println("buscarAsistenciasAlumno");
        Alumno alumno = null;
        Date fecha = null;
        AsistenciaResource instance = new AsistenciaResource();
        List<Asistencia> expResult = null;
        List<Asistencia> result = instance.buscarAsistenciasAlumno(alumno, fecha);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
