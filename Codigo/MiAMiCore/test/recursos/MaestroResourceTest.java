package recursos;

import controladores.MaestroJpaControllerExtended;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Maestro;
import modelo.Pagodesalario;
import org.junit.Test;
import static org.junit.Assert.*;

public class MaestroResourceTest {
    EntityManagerFactory emf;
    Maestro test;
    public MaestroResourceTest() {
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
        test = new Maestro();
        test.setActivo(true);
        test.setApellidos("Ronzon");
        test.setCorreo("missaxp@gmail.com");
        test.setNombre("Misa el teacher test");
        test.setTelefono("2323232323");
        
        
    }

    @Test
    public void testRegistrarMaestro() {
        System.out.println("registrarMaestro");
        MaestroResource instance = new MaestroResource();
        boolean expResult = true;
        boolean result = instance.registrarMaestro(test);
        assertEquals(expResult, result);
        
    }

    //@Test
    public void testEliminarMaestro() throws Exception {
        System.out.println("eliminarMaestro");
        Maestro maestro = test;
        MaestroResource instance = new MaestroResource();
        boolean expResult = false;
        boolean result = instance.eliminarMaestro(maestro);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testModificarMaestro() throws Exception {
        System.out.println("modificarMaestro");
        MaestroJpaControllerExtended control = new MaestroJpaControllerExtended(emf);
        
        Maestro maestro = control.findMaestro(1);
        maestro.setNombre("Modificado por modificarMaestroText");
        MaestroResource instance = new MaestroResource();
        boolean expResult = true;
        boolean result = instance.modificarMaestro(maestro);
        assertEquals(expResult, result);
    }

    @Test
    public void testBuscarMaestroPorIdentificador() {
        System.out.println("buscarMaestroPorIdentificador");
        int id = 1;
        MaestroResource instance = new MaestroResource();
        Maestro result = instance.buscarMaestroPorIdentificador(id);
        assertNotNull(result);
    }

    @Test
    public void testGetPagosDeSalario() {
        System.out.println("getPagosDeSalario");
        int idMaestro = 1;
        MaestroResource instance = new MaestroResource();
        
        List<Pagodesalario> result = instance.getPagosDeSalario(idMaestro);
        assertNotNull(result);
    }

    @Test
    public void testGetMaestros() {
        System.out.println("getMaestros");
        MaestroResource instance = new MaestroResource();
        List<Maestro> result = instance.getMaestros();
        assertNotNull(result);
    }
    
}
