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
    

    public List<Asistencia> buscarAsistenciasAlumno(Alumno alumno, Date fecha){
        AsistenciaJpaControllerExtended control = new AsistenciaJpaControllerExtended(emf);
        return control.getAsistenciaAlumno(alumno, fecha);
    }
}
