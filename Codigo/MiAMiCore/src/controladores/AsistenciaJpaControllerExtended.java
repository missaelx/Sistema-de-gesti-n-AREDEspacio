package controladores;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TemporalType;
import modelo.Alumno;
import modelo.Asistencia;

/**
 *
 * @author macbookpro
 */
public class AsistenciaJpaControllerExtended extends AsistenciaJpaController{
    
    public AsistenciaJpaControllerExtended(EntityManagerFactory emf) {
        super(emf);
    }

    public List<Asistencia> getAsistenciaAlumno(Alumno alumno, Date fecha) {
        EntityManager em = getEntityManager();
        List<Asistencia> Asistencias = (List<Asistencia>) em.createNamedQuery("Asistencia.findByAlumno")
                .setParameter("idAlumno", alumno)
                .setParameter("fecha",fecha, TemporalType.DATE)
                .getResultList();
        
        return Asistencias;
    }
    
    
    
}
