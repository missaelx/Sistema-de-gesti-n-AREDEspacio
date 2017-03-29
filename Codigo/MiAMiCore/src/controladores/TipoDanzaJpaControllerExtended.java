package controladores;

<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AndrésRoberto
 */
public class TipoDanzaJpaControllerExtended {
=======
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.TipoDanza;

/**
 *
 * @author macbookpro
 */
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
>>>>>>> 5e5f189d65573d4f7647f48224037963c2d0ce7a
    
}
