package controladores;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Alumno;

/**
 *
 * @author macbookpro
 */
public class AlumnoJpaControllerExtended extends AlumnoJpaController{
    
    public AlumnoJpaControllerExtended(EntityManagerFactory emf) {
        super(emf);
    }
    
    public List<Alumno> getAlumnoFromNombre(String nombre){
        EntityManager em = getEntityManager();
        List<Alumno> alumno = (List<Alumno>) em.createNamedQuery("Alumno.findByNombreLike").setParameter("nombre", nombre).getResultList();
        em.close();
        return alumno;
    }
    public Alumno getAlumnoFromApellidos(String apellidos){
        EntityManager em = getEntityManager();
        Alumno alumno = (Alumno) em.createNamedQuery("Alumno.findByApellidos").setParameter("apellidos", apellidos).getSingleResult();
        em.close();
        return alumno;
    }
    public List<Alumno> getAlumnoFromCorreo(String correo){
        EntityManager em = getEntityManager();
        List<Alumno> alumno = (List<Alumno>) em.createNamedQuery("Alumno.findByCorreo").setParameter("correo", correo).getResultList();
        em.close();
        return alumno;
    }
    
    
    public List<Alumno> getAllAlumnos(){
        EntityManager em = getEntityManager();
        List<Alumno> alumno = (List<Alumno>) em.createNamedQuery("Alumno.findAll").getResultList();
        em.close();
        return alumno;
    }
    
    public List<Alumno> getProximasReinscripciones(Date inicio, Date fin){
        EntityManager em = getEntityManager();
        List<Alumno> alumno = (List<Alumno>) em.createNamedQuery("Alumno.findReinscripcionesProximas")
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .getResultList();
        em.close();
        return alumno;
    }
    
}
