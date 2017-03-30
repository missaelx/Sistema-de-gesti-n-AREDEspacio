package controladores;

<<<<<<< HEAD
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AndrésRoberto
 */
>>>>>>> fbce2922458000d81662a9361cf9b243293dd84c
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.TipoDanza;


public class TipoDanzaJpaControllerExtended extends TipoDanzaJpaController{
    
    public TipoDanzaJpaControllerExtended(EntityManagerFactory emf) {
        super(emf);
    }

    
    public List<TipoDanza> getAll(){
        EntityManager em = getEntityManager();
        List<TipoDanza> danzas = (List<TipoDanza>) em.createNamedQuery("TipoDanza.findAll").getResultList();
        em.close();
        return danzas;
    }
<<<<<<< HEAD
   
=======
    
>>>>>>> fbce2922458000d81662a9361cf9b243293dd84c
}
