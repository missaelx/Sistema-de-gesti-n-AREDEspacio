/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import java.util.List;
import modelo.Promociones;
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
public class PromocionesResourceTest {
    
    public PromocionesResourceTest() {
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
     * Test of crearPromocion method, of class PromocionesResource.
     */
    @Test
    public void testCrearPromocion() {
        System.out.println("crearPromocion");
        Promociones promocion = null;
        PromocionesResource instance = new PromocionesResource();
        boolean expResult = false;
        boolean result = instance.crearPromocion(promocion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminarPromocion method, of class PromocionesResource.
     */
    @Test
    public void testEliminarPromocion() throws Exception {
        System.out.println("eliminarPromocion");
        Promociones promocion = null;
        PromocionesResource instance = new PromocionesResource();
        boolean expResult = false;
        boolean result = instance.eliminarPromocion(promocion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editarPromocion method, of class PromocionesResource.
     */
    @Test
    public void testEditarPromocion() throws Exception {
        System.out.println("editarPromocion");
        Promociones promocion = null;
        PromocionesResource instance = new PromocionesResource();
        boolean expResult = false;
        boolean result = instance.editarPromocion(promocion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getActivos method, of class PromocionesResource.
     */
    @Test
    public void testGetActivos() {
        System.out.println("getActivos");
        PromocionesResource instance = new PromocionesResource();
        List<Promociones> expResult = null;
        List<Promociones> result = instance.getActivos();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPromoMensualidad method, of class PromocionesResource.
     */
    @Test
    public void testGetPromoMensualidad() {
        System.out.println("getPromoMensualidad");
        PromocionesResource instance = new PromocionesResource();
        List<Promociones> expResult = null;
        List<Promociones> result = instance.getPromoMensualidad();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPromoInscripcion method, of class PromocionesResource.
     */
    @Test
    public void testGetPromoInscripcion() {
        System.out.println("getPromoInscripcion");
        PromocionesResource instance = new PromocionesResource();
        List<Promociones> expResult = null;
        List<Promociones> result = instance.getPromoInscripcion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAll method, of class PromocionesResource.
     */
    @Test
    public void testGetAll() {
        System.out.println("getAll");
        PromocionesResource instance = new PromocionesResource();
        List<Promociones> expResult = null;
        List<Promociones> result = instance.getAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
