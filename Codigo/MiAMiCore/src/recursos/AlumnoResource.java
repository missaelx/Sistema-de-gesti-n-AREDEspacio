package recursos;

import controladores.AlumnoJpaController;
import controladores.AlumnoJpaControllerExtended;
import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Alumno;


/**
 *
 * @author Missael Hernández Rosado
 */
public class AlumnoResource {
    EntityManagerFactory emf;
    public AlumnoResource(){
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
    }
    
    public boolean registrarAlumno(Alumno alumno){
        boolean result = true;
        AlumnoJpaController alumnosController = new AlumnoJpaController(emf);
        alumnosController.create(alumno);
        return result;
    }
    public boolean eliminarAlumno(Alumno alumno) throws NonexistentEntityException, Exception{
        boolean result = true;
        alumno.setActivo(false);
        modificarAlumno(alumno);
        return result;
    }
    public boolean modificarAlumno(Alumno alumno) throws NonexistentEntityException, Exception{
        boolean result = true;
        AlumnoJpaController alumnosController = new AlumnoJpaController(emf);
        try {
            alumnosController.edit(alumno);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(AlumnoResource.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        }
        return result;
    }
    
    
    
    
    public List<Alumno> visualizarRegistros() {
        AlumnoJpaController alumnosController = new AlumnoJpaController(emf);
        return alumnosController.findAlumnoEntities();
    }
    public Alumno getAlumnoPorId(int id){
        AlumnoJpaController alumnosController = new AlumnoJpaController(emf);
        return alumnosController.findAlumno(id);
    }
    public Alumno buscarAlumnoPorNombre(String nombre){
        AlumnoJpaControllerExtended alumnosController = new AlumnoJpaControllerExtended(emf);
        return alumnosController.getAlumnoFromNombre(nombre);
    }
}
