package controladores;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Gastovariable;
import modelo.Inscripcion;

/**
 *
 * @author macbookpro
 */
public class InscripcionJpaControllerExtended extends InscripcionJpaController{
    
    public InscripcionJpaControllerExtended(EntityManagerFactory emf) {
        super(emf);
    }

    public List<Inscripcion> getInscripcionesPorFechas(Date inicio, Date fin) {
        EntityManager em = getEntityManager();
        List<Inscripcion> res = em.createNamedQuery("Inscripcion.findEntreFechas")
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .getResultList();
        
        em.close();
        return res;
    }
    
}
