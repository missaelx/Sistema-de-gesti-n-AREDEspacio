package controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.GrupoClase;
import modelo.Horario;

/**
 *
 * @author macbookpro
 */
public class HorarioJpaControllerExtended extends HorarioJpaController{
    
    public HorarioJpaControllerExtended(EntityManagerFactory emf) {
        super(emf);
    }
    
    public List<Horario> getHorariosFromDia(String dia){
        EntityManager em = getEntityManager();
        List<Horario> horarios = (List<Horario>) em.createNamedQuery("Horario.findClaseHoy")
                .setParameter("dia", dia)
                .getResultList();
        em.close();
        return horarios;
    }
    
}
