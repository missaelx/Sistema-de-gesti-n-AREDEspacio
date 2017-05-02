package controladores;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Gastovariable;

/**
 *
 * @author macbookpro
 */
public class GastovariableJpaControllerExtended extends GastovariableJpaController{
    
    public GastovariableJpaControllerExtended(EntityManagerFactory emf) {
        super(emf);
    }
    
    public List<Gastovariable> getEgresosPorFechas(Date inicio, Date fin){
        EntityManager em = getEntityManager();
        List<Gastovariable> res = em.createNamedQuery("Gastovariable.findEntreFechas").setParameter("inicio", inicio).setParameter("fin", fin).getResultList();
        
        em.close();
        return res;
    }
    
}
