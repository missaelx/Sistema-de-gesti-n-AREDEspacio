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
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import recursos.MaestroResource;

/**
 *
 * @author AndrésRoberto
 */
public class MaestroJpaControllerExtTest {
    
    EntityManagerFactory emf;
    List<Maestro> listaDeMaestros;
    
    public MaestroJpaControllerExtTest(){
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
    }
    @Test
    public void testGetAllMaestros(){
        System.out.println("Obtener todos los maestros, lista con maestros");
        MaestroJpaControllerExtended instancia = new MaestroJpaControllerExtended(emf);
        MaestroResource recurso = new MaestroResource();
        List<Maestro> espResultado = recurso.visualizarRegistros();
        List<Maestro> resultado = instancia.getAllMaestros();
        assertEquals(espResultado, resultado);
    }
    @Test
    public void testGetAllMaestrosNull(){
        System.out.println("Obtener todos los maestros, lista nula");
        MaestroJpaControllerExtended instancia = new MaestroJpaControllerExtended(emf);
        MaestroResource recurso = new MaestroResource();
        List<Maestro> espResultado = null;
        List<Maestro> resultado = listaDeMaestros;
        assertEquals(espResultado, resultado);
    }
    @Test
    public void testGetPorNombre(){
        System.out.println("Obtener todos los maestros, lista por nombre");
        MaestroJpaControllerExtended instancia = new MaestroJpaControllerExtended(emf);
        MaestroResource recurso = new MaestroResource();
        List<Maestro> espResultado = recurso.buscarMaestroNombre("Jose");
        List<Maestro> resultado = instancia.getMaestroFromNombre("Jose");
        
    }
    
    
    
    
    
    
    
}
