package recursos;

import controladores.GrupoClaseJpaController;
import controladores.TipoDanzaJpaController;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.GrupoClase;
import modelo.Maestro;
import modelo.TipoDanza;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author macbookpro
 */
public class DanzaResourceTest {
    
    EntityManagerFactory emf;
    TipoDanza tipoDanzaTest;
    GrupoClase grupoTest;
    
    public DanzaResourceTest() {
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
        tipoDanzaTest = new TipoDanza(1);
        tipoDanzaTest.setActivo(true);
        tipoDanzaTest.setDescripcion("Descripcion de prueba");
        tipoDanzaTest.setNombre("TipoDanza prueba de nombre");
        
        grupoTest = new GrupoClase(1);
        grupoTest.setActivo(true);
        grupoTest.setCostoMensual(new BigDecimal(1323.32));
        grupoTest.setIdTipoDanza(tipoDanzaTest);
        grupoTest.setTerminado(false);
        grupoTest.setIdMaestro(new Maestro(1));
        
    }

    @Test
    public void testCrearDanza() {
        System.out.println("crearDanza");
        DanzaResource instance = new DanzaResource();
        boolean expResult = true;
        boolean result = instance.crearDanza(tipoDanzaTest);
        assertEquals(expResult, result);
    }

    @Test
    public void testModificarDanza() throws Exception {
        System.out.println("modificarDanza");
        TipoDanzaJpaController controlador = new TipoDanzaJpaController(emf);
        TipoDanza tipoDanza = controlador.findTipoDanza(1);
        tipoDanza.setDescripcion("Descripcion de prueba modificado");
        
        DanzaResource instance = new DanzaResource();
        
        boolean expResult = true;
        boolean result = instance.modificarDanza(tipoDanza);
        assertEquals(expResult, result);
    }

    @Test
    public void testEliminarDanza() throws Exception {
        System.out.println("eliminarDanza");
        
        TipoDanzaJpaController controlador = new TipoDanzaJpaController(emf);
        TipoDanza tipoDanza = controlador.findTipoDanza(1);
        
        DanzaResource instance = new DanzaResource();
        
        boolean expResult = true;
        
        boolean result = instance.eliminarDanza(tipoDanza);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetTiposDanza() {
        System.out.println("getTiposDanza");
        DanzaResource instance = new DanzaResource();
        List<TipoDanza> result = instance.getTiposDanza();
        assertNotNull(result);
        
    }

    @Test
    public void testCrearGrupoClase() {
        System.out.println("crearGrupoClase");
        GrupoClase grupo = grupoTest;
        DanzaResource instance = new DanzaResource();
        boolean expResult = true;
        boolean result = instance.crearGrupoClase(grupo);
        assertEquals(expResult, result);
    }

    @Test
    public void testModificarGrupoClase() throws Exception {
        System.out.println("modificarGrupoClase");
        GrupoClaseJpaController controller = new GrupoClaseJpaController(emf);
        
        GrupoClase grupo = controller.findGrupoClase(1);
        grupo.setFechaTermino("Fecha de termino modificada");
        
        DanzaResource instance = new DanzaResource();
        boolean expResult = true;
        boolean result = instance.modificarGrupoClase(grupo);
        assertEquals(expResult, result);
    }

    @Test
    public void testEliminarGrupoClase() throws Exception {
        System.out.println("eliminarGrupoClase");
        GrupoClaseJpaController controller = new GrupoClaseJpaController(emf);
        
        GrupoClase grupo = controller.findGrupoClase(1);
        DanzaResource instance = new DanzaResource();
        boolean expResult = true;
        boolean result = instance.eliminarGrupoClase(grupo);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetGruposClase() {
        System.out.println("getGruposClase");
        DanzaResource instance = new DanzaResource();
        List<GrupoClase> result = instance.getGruposClase();
        assertNotNull(result);
    }

    @Test
    public void testGetGrupoClase() {
        System.out.println("getGrupoClase");
        int idGrupo = 1;
        DanzaResource instance = new DanzaResource();
        GrupoClase result = instance.getGrupoClase(idGrupo);
        assertNotNull(result);
    }

   
}
