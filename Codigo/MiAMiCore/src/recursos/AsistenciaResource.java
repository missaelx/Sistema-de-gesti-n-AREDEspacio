package recursos;

import controladores.AsistenciaJpaController;
import controladores.AsistenciaJpaControllerExtended;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Alumno;
import modelo.Asistencia;

/**
 *
 * @author Miguel Acosta
 */
public class AsistenciaResource {
    
    EntityManagerFactory emf;
    public AsistenciaResource(){
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
    }
    
    public boolean registrarAsistencia(Asistencia asistencia){
        AsistenciaJpaController control = new AsistenciaJpaController(emf);
        control.create(asistencia);
        return true;
    }
    
<<<<<<< HEAD
    public List<Asistencia> buscarAsistenciasAlumno(int alumno, Date fecha){
        AsistenciaJpaController control = new AsistenciaJpaController(emf);
=======
    public List<Asistencia> buscarAsistenciasAlumno(Alumno alumno, Date fecha){
        AsistenciaJpaControllerExtended control = new AsistenciaJpaControllerExtended(emf);
>>>>>>> 88846d774b9ef3f30282d81e44416635e1cd67c9
        return control.getAsistenciaAlumno(alumno, fecha);
    }
}
