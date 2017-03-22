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
        return (Alumno) em.createNamedQuery("Alumnos.findByNombre").setParameter("nombre", nombre).getSingleResult();
    }

    
}
