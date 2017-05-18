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
    
    /**
     * Consigue una lista de asistencia cuyos elementos en su propiedad "fecha" son mayores al parametro fecha
     * @param alumno El alumno que se buscaran sus fechas
     * @param fecha La fecha de inicio para buscar [fecha, ...}
     * @return Lista de asistencias del alumno desde la fecha definida hasta el registro mas reciente
     */
    public List<Asistencia> getAsistenciaAlumno(Alumno alumno, Date fecha) {
        EntityManager em = getEntityManager();
        List<Asistencia> Asistencias = (List<Asistencia>) em.createNamedQuery("Asistencia.findByAlumno")
                .setParameter("idAlumno", alumno)
                .setParameter("fecha",fecha, TemporalType.DATE)
                .getResultList();
        
        return Asistencias;
    }
    
    
    
}
