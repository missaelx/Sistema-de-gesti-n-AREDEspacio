package controladores;

import java.util.Date;
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
        List<Pagodesalario> lista = (List<Pagodesalario>) em.createNamedQuery("Pagodesalario.findByMaestro").setParameter("idmaestro", idMaestro).getResultList();
        
        em.close();
        return lista;
    }
    
    public List<Pagodesalario> getPagoSalarioEntreFechas(Date inicio, Date fin){
        EntityManager em = getEntityManager();
        List<Pagodesalario> lista = (List<Pagodesalario>) em.createNamedQuery("Pagodesalario.findEntreFechas")
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .getResultList();
        
        em.close();
        return lista;
    }

    
}
