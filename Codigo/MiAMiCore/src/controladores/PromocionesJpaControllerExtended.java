
package controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Promociones;

/**
 *
 * @author Miguel Acosta
 */
public class PromocionesJpaControllerExtended  extends PromocionesJpaController{
    
    public PromocionesJpaControllerExtended(EntityManagerFactory emf){
        super (emf);
    }
    
    public List<Promociones> getPromocionesActivas(){
        EntityManager em = getEntityManager();
        List<Promociones> promociones = (List<Promociones>) em.createNamedQuery("promociones.findByActivo").getResultList();
        em.close();
        return promociones;
    }

    public List<Promociones> getPromocionesMensualidad(){
        EntityManager entityManager = getEntityManager();
        List<Promociones> promociones = (List<Promociones>) entityManager.createNamedQuery("promociones.findByMensualidad").getResultList();
        entityManager.close();
        return promociones;
    }

    public List<Promociones> getPromocionesInscripcion(){
        EntityManager em = getEntityManager();
        List<Promociones> promociones = (List<Promociones>) em.createNamedQuery("promociones.findByInscripcion").getResultList();
        em.close();
        return promociones;
    }
}
