/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Maestro;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AndrésRoberto
 */
public class MaestroJpaControllerExtendedTest {
    EntityManagerFactory emf;
    
    public MaestroJpaControllerExtendedTest() {
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
    }

    @Test
    public void testGetMaestroFromNombre() {
        System.out.println("getMaestroFromNombre");
        String nombre = "";
        MaestroJpaControllerExtended instance = null;
        List<Maestro> expResult = null;
        List<Maestro> result = instance.getMaestroFromNombre(nombre);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetAllMaestros() {
        System.out.println("getAllMaestros");
        MaestroJpaControllerExtended instance = null;
        List<Maestro> expResult = null;
        List<Maestro> result = instance.getAllMaestros();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    
}
