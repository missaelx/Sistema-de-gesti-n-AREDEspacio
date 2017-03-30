/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.TipoDanza;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import recursos.DanzaResource;

/**
 *
 * @author AndrésRoberto
 */
public class TipoDanzaJpaControllerExtTest {
    
    EntityManagerFactory emf;
    List<TipoDanza> listaDeDanzas;

    public TipoDanzaJpaControllerExtTest() {
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
        
        
    }
    
    @Test
    public void testGetALLNull(){
        System.out.println("Obtener Todas las danzas lista nula");
        TipoDanzaJpaControllerExtended instancia = new TipoDanzaJpaControllerExtended(emf);
        List<TipoDanza> espResultado = listaDeDanzas;
        //List<TipoDanza> resultado = instancia.getAll();
        List<TipoDanza> resultado = null;
        assertEquals(espResultado, resultado);
    }
    
    @Test
    public void testGetALL(){
        System.out.println("Obtener Todas las danzas lista con danzas");
        TipoDanzaJpaControllerExtended instancia = new TipoDanzaJpaControllerExtended(emf);
        DanzaResource recurso = new DanzaResource();
        List<TipoDanza> espResultado = recurso.visualizarRegistros();;
        List<TipoDanza> resultado = instancia.getAll();
        assertEquals(espResultado, resultado);
    }
    
}
