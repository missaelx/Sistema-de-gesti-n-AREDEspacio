/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import java.util.List;
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
public class GrupoClaseResourceTest {
    
    public GrupoClaseResourceTest() {
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
     * Test of getClasesImpartidasPorMaestro method, of class GrupoClaseResource.
     */
    @Test
    public void testGetClasesImpartidasPorMaestro() {
        System.out.println("getClasesImpartidasPorMaestro");
        int idMaestro = 0;
        GrupoClaseResource instance = new GrupoClaseResource();
        List<GrupoClase> expResult = null;
        List<GrupoClase> result = instance.getClasesImpartidasPorMaestro(idMaestro);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGrupoClasePorId method, of class GrupoClaseResource.
     */
    @Test
    public void testGetGrupoClasePorId() {
        System.out.println("getGrupoClasePorId");
        Integer id = null;
        GrupoClaseResource instance = new GrupoClaseResource();
        GrupoClase expResult = null;
        GrupoClase result = instance.getGrupoClasePorId(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getClasesFromDia method, of class GrupoClaseResource.
     */
    @Test
    public void testGetClasesFromDia() {
        System.out.println("getClasesFromDia");
        int weekDay = 0;
        GrupoClaseResource instance = new GrupoClaseResource();
        List<GrupoClase> expResult = null;
        List<GrupoClase> result = instance.getClasesFromDia(weekDay);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
