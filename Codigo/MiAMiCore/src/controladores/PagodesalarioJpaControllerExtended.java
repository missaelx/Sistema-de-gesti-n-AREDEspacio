package controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Pagodesalario;

/**
 *
 * @author macbookpro
 */
public class PagodesalarioJpaControllerExtended extends PagodesalarioJpaController{
    
    public PagodesalarioJpaControllerExtended(EntityManagerFactory emf) {
        super(emf);
    }
    
    public List<Pagodesalario> getPagoSalarioFromMaestro(int idMaestro){
        EntityManager em = getEntityManager();
        return em.createNamedQuery("Pagosdesalario.findByMaestro").setParameter("idmaestro", idMaestro).getResultList();
    }

    
}
