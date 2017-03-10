package recursos;

import controladores.AlumnosJpaController;
import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Alumnos;


/**
 *
 * @author Missael Hernández Rosado
 */
public class AlumnoResource {
    EntityManagerFactory emf;
    EntityManager em;
    public AlumnoResource(){
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
        em = emf.createEntityManager();
    }
    
    public boolean registrarAlumno(Alumnos alumno){
        boolean result = true;
        AlumnosJpaController alumnosController = new AlumnosJpaController(emf);
        alumnosController.create(alumno);
        return result;
    }
    public boolean eliminarAlumno(Alumnos alumno) throws NonexistentEntityException, Exception{
        boolean result = true;
        alumno.setActivo(false);
        modificarAlumno(alumno);
        return result;
    }
    public boolean modificarAlumno(Alumnos alumno) throws NonexistentEntityException, Exception{
        boolean result = true;
        AlumnosJpaController alumnosController = new AlumnosJpaController(emf);
        try {
            alumnosController.edit(alumno);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(AlumnoResource.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        }
        return result;
    }
    
    
    
    
    public List<Alumnos> visualizarRegistros() {
        AlumnosJpaController alumnosController = new AlumnosJpaController(emf);
        return alumnosController.findAlumnosEntities();
    }    
}
