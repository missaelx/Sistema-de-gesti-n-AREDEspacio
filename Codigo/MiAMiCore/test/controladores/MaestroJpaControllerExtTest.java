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
    
    public void testGetAllMaestros(){
        System.out.println("Obtener todos los maestros, lista con maestros");
        MaestroJpaControllerExtended instancia = new MaestroJpaControllerExtended(emf);
        MaestroResource recurso = new MaestroResource();
        List<Maestro> espResultado = recurso.visualizarRegistros();
        List<Maestro> resultado = instancia.getAllMaestros();
        assertEquals(espResultado, resultado);
    }
    
    public void testGetAllMaestrosNull(){
        System.out.println("Obtener todos los maestros, lista con nula");
        MaestroJpaControllerExtended instancia = new MaestroJpaControllerExtended(emf);
        MaestroResource recurso = new MaestroResource();
        List<Maestro> espResultado = null;
        List<Maestro> resultado = listaDeMaestros;
        assertEquals(espResultado, resultado);
    }
    
    public void testGetPorNombre(){
        
    }
    
    
    
    
    
}
