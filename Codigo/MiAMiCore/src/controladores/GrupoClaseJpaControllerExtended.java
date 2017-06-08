package controladores;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Alumno;
import modelo.GrupoClase;
import modelo.TipoDanza;

/**
 *
 * @author AndrésRoberto
 */
public class GrupoClaseJpaControllerExtended extends GrupoClaseJpaController{
    
    public GrupoClaseJpaControllerExtended(EntityManagerFactory emf) {
        super(emf);
    }

    
    public List<GrupoClase> getAll(){
        EntityManager em = getEntityManager();
        List<GrupoClase> grupoClase = (List<GrupoClase>) em.createNamedQuery("GrupoClase.findAll").getResultList();
        em.close();
        return grupoClase;
    }
    
    public List<GrupoClase> getGruposFromMaestro(int idMaestro){
        EntityManager em = getEntityManager();
        List<GrupoClase> grupoClase = (List<GrupoClase>) em.createNamedQuery("GrupoClase.findByMaestro")
                .setParameter("idMaestro", idMaestro)
                .getResultList();
        em.close();
        return grupoClase;
    }
    
    public List<GrupoClase> findGrupoPorTipoDanza(TipoDanza tipo){
        EntityManager em = getEntityManager();
        List<GrupoClase> grupoClase = (List<GrupoClase>) em.createNamedQuery("GrupoClase.findByTipoDanza")
                .setParameter("idTipoDanza", tipo.getId())
                .getResultList();
        em.close();
        return grupoClase;
    }
    
    public List<GrupoClase> findGrupoPorAlumno(Alumno alumno){
        EntityManager em = getEntityManager();
        List<GrupoClase> grupoClase = (List<GrupoClase>) em.createNamedQuery("GrupoClase.findByAlumno")
                .setParameter("alumno", alumno)
                .getResultList();
        em.close();
        return grupoClase;
    }
}
