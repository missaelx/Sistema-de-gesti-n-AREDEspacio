package controladores;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Mensualidad;


/**
 *
 * @author macbookpro
 */
public class MensualidadJpaControllerExtended extends MensualidadJpaController{
    
    public MensualidadJpaControllerExtended(EntityManagerFactory emf) {
        super(emf);
    }

    public List<Mensualidad> getMensualidadesEntreFechas(Date inicio, Date fin) {
        EntityManager em = getEntityManager();
        List<Mensualidad> res = em.createNamedQuery("Mensualidad.findEntreFechas")
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .getResultList();
        
        em.close();
        return res;
    }
    
}
