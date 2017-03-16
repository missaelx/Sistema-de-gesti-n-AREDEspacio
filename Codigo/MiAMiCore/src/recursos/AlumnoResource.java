package recursos;

import controladores.AlumnosJpaController;
import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Alumnos;


/**
 *
 * @author Missael Hernández Rosado
 */
public class AlumnoResource {
    EntityManagerFactory emf;
    public AlumnoResource(){
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
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
    public Alumnos getAlumnoPorId(int id){
        AlumnosJpaController alumnosController = new AlumnosJpaController(emf);
        return alumnosController.findAlumnos(id);
    }
}
