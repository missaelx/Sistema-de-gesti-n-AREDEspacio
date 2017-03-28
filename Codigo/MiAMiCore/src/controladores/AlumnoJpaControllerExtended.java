package controladores;

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
    
    public Alumno getAlumnoFromNombre(String nombre){
        EntityManager em = getEntityManager();
        Alumno alumno = (Alumno) em.createNamedQuery("Alumno.findByNombre").setParameter("nombre", nombre).getSingleResult();
        em.close();
        return alumno;
    }
    public Alumno getAlumnoFromApellidos(String apellidos){
        EntityManager em = getEntityManager();
        Alumno alumno = (Alumno) em.createNamedQuery("Alumno.findByApellidos").setParameter("apellidos", apellidos).getSingleResult();
        em.close();
        return alumno;
    }
    public Alumno getAlumnoFromCorreo(String correo){
        EntityManager em = getEntityManager();
        Alumno alumno = (Alumno) em.createNamedQuery("Alumno.findByCorreo").setParameter("correo", correo).getSingleResult();
        em.close();
        return alumno;
    
    }
}
