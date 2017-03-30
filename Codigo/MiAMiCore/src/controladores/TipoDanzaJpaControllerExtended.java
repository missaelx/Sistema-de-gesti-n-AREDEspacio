package controladores;

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
}
