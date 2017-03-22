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
        Alumno alumno = (Alumno) em.createNamedQuery("Alumnos.findByNombre").setParameter("nombre", nombre).getSingleResult();
        em.close();
        return alumno;
    }

    
}
