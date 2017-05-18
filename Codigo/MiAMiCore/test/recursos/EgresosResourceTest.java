/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import java.util.Date;
import java.util.List;
import modelo.Gastovariable;
import modelo.Pagodesalario;
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
public class EgresosResourceTest {
    
    public EgresosResourceTest() {
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
     * Test of getEgresosVariables method, of class EgresosResource.
     */
    @Test
    public void testGetEgresosVariables() {
        System.out.println("getEgresosVariables");
        EgresosResource instance = new EgresosResource();
        List<Gastovariable> expResult = null;
        List<Gastovariable> result = instance.getEgresosVariables();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPagosSalariales method, of class EgresosResource.
     */
    @Test
    public void testGetPagosSalariales() {
        System.out.println("getPagosSalariales");
        EgresosResource instance = new EgresosResource();
        List<Pagodesalario> expResult = null;
        List<Pagodesalario> result = instance.getPagosSalariales();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registrarPagoSalario method, of class EgresosResource.
     */
    @Test
    public void testRegistrarPagoSalario() {
        System.out.println("registrarPagoSalario");
        Pagodesalario pago = null;
        EgresosResource instance = new EgresosResource();
        boolean expResult = false;
        boolean result = instance.registrarPagoSalario(pago);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registrarEgreso method, of class EgresosResource.
     */
    @Test
    public void testRegistrarEgreso() {
        System.out.println("registrarEgreso");
        Gastovariable pago = null;
        EgresosResource instance = new EgresosResource();
        boolean expResult = false;
        boolean result = instance.registrarEgreso(pago);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminarPagoSalario method, of class EgresosResource.
     */
    @Test
    public void testEliminarPagoSalario() {
        System.out.println("eliminarPagoSalario");
        Pagodesalario pago = null;
        EgresosResource instance = new EgresosResource();
        boolean expResult = false;
        boolean result = instance.eliminarPagoSalario(pago);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminarEgreso method, of class EgresosResource.
     */
    @Test
    public void testEliminarEgreso() {
        System.out.println("eliminarEgreso");
        Gastovariable pago = null;
        EgresosResource instance = new EgresosResource();
        boolean expResult = false;
        boolean result = instance.eliminarEgreso(pago);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEgresosEntreFechas method, of class EgresosResource.
     */
    @Test
    public void testGetEgresosEntreFechas() {
        System.out.println("getEgresosEntreFechas");
        Date inicio = null;
        Date fin = null;
        EgresosResource instance = new EgresosResource();
        List<Gastovariable> expResult = null;
        List<Gastovariable> result = instance.getEgresosEntreFechas(inicio, fin);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSalariosEntreFechas method, of class EgresosResource.
     */
    @Test
    public void testGetSalariosEntreFechas() {
        System.out.println("getSalariosEntreFechas");
        Date inicio = null;
        Date fin = null;
        EgresosResource instance = new EgresosResource();
        List<Pagodesalario> expResult = null;
        List<Pagodesalario> result = instance.getSalariosEntreFechas(inicio, fin);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
