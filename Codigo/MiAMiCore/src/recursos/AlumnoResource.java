package recursos;

import controladores.AlumnosJpaController;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Alumnos;
import org.eclipse.persistence.internal.jpa.EntityManagerFactoryImpl;

/**
 *
 * @author Missael Hernández Rosado
 */
public class AlumnoResource {
    public boolean registrarAlumno(Alumnos alumno){
        boolean result = true;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
        EntityManager em = emf.createEntityManager();
        
        AlumnosJpaController alumnosController = new AlumnosJpaController(emf);
        alumnosController.create(alumno);
        
        return result;
    }
    public boolean eliminarAlumno(Alumnos alumno){
        boolean result = true;
        return result;
    }
    public boolean modificarAlumno(Alumnos alumno){
        boolean result = true;
        return result;
    }
    
    
    
    
    public List<Alumnos> visualizarRegistros() {
        throw new UnsupportedOperationException();
    }
    
    
}
