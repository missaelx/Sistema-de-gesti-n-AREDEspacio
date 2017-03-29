package controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Maestro;
import modelo.Pagodesalario;

/**
 *
 * @author macbookpro
 */
public class MaestroJpaControllerExtended extends MaestroJpaController{
    
    public MaestroJpaControllerExtended(EntityManagerFactory emf) {
        super(emf);
    }
    public List<Maestro> getMaestroFromNombre(String nombre){
        EntityManager em = getEntityManager();
        List<Maestro> maestro = (List<Maestro>) em.createNamedQuery("Maestro.findByNombreLike").setParameter("nombre", nombre).getResultList();
        em.close();
        return maestro;
    }
    
    public List<Maestro> getAllMaestros(){
        EntityManager em = getEntityManager();
        List<Maestro> maestro = (List<Maestro>) em.createNamedQuery("Maestro.findAll").getResultList();
        em.close();
        return maestro;
    }

    
}
