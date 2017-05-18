/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import java.util.Date;
import java.util.List;
import modelo.Ingreso;
import modelo.Inscripcion;
import modelo.Mensualidad;
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
public class IngresosResourceTest {
    
    public IngresosResourceTest() {
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
     * Test of getInscripciones method, of class IngresosResource.
     */
    @Test
    public void testGetInscripciones() {
        System.out.println("getInscripciones");
        IngresosResource instance = new IngresosResource();
        List<Inscripcion> expResult = null;
        List<Inscripcion> result = instance.getInscripciones();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMensualidades method, of class IngresosResource.
     */
    @Test
    public void testGetMensualidades() {
        System.out.println("getMensualidades");
        IngresosResource instance = new IngresosResource();
        List<Mensualidad> expResult = null;
        List<Mensualidad> result = instance.getMensualidades();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registrarPagoMensualidad method, of class IngresosResource.
     */
    @Test
    public void testRegistrarPagoMensualidad() {
        System.out.println("registrarPagoMensualidad");
        Mensualidad pago = null;
        IngresosResource instance = new IngresosResource();
        boolean expResult = false;
        boolean result = instance.registrarPagoMensualidad(pago);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registrarInscripcion method, of class IngresosResource.
     */
    @Test
    public void testRegistrarInscripcion() {
        System.out.println("registrarInscripcion");
        Inscripcion pago = null;
        IngresosResource instance = new IngresosResource();
        boolean expResult = false;
        boolean result = instance.registrarInscripcion(pago);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminarMensualidad method, of class IngresosResource.
     */
    @Test
    public void testEliminarMensualidad() {
        System.out.println("eliminarMensualidad");
        Mensualidad pago = null;
        IngresosResource instance = new IngresosResource();
        boolean expResult = false;
        boolean result = instance.eliminarMensualidad(pago);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminarInscripcion method, of class IngresosResource.
     */
    @Test
    public void testEliminarInscripcion() {
        System.out.println("eliminarInscripcion");
        Inscripcion pago = null;
        IngresosResource instance = new IngresosResource();
        boolean expResult = false;
        boolean result = instance.eliminarInscripcion(pago);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInscripcionesEntreFechas method, of class IngresosResource.
     */
    @Test
    public void testGetInscripcionesEntreFechas() {
        System.out.println("getInscripcionesEntreFechas");
        Date inicio = null;
        Date fin = null;
        IngresosResource instance = new IngresosResource();
        List<Inscripcion> expResult = null;
        List<Inscripcion> result = instance.getInscripcionesEntreFechas(inicio, fin);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMensualidadesEntreFechas method, of class IngresosResource.
     */
    @Test
    public void testGetMensualidadesEntreFechas() {
        System.out.println("getMensualidadesEntreFechas");
        Date inicio = null;
        Date fin = null;
        IngresosResource instance = new IngresosResource();
        List<Mensualidad> expResult = null;
        List<Mensualidad> result = instance.getMensualidadesEntreFechas(inicio, fin);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registrarIngreso method, of class IngresosResource.
     */
    @Test
    public void testRegistrarIngreso() {
        System.out.println("registrarIngreso");
        Ingreso ingreso = null;
        IngresosResource instance = new IngresosResource();
        boolean expResult = false;
        boolean result = instance.registrarIngreso(ingreso);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
