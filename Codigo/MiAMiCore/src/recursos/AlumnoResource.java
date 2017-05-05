package recursos;

import controladores.AlumnoJpaController;
import controladores.AlumnoJpaControllerExtended;
import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Alumno;
import modelo.GrupoClase;


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
        AlumnoJpaControllerExtended alumnosController = new AlumnoJpaControllerExtended(emf);
        return alumnosController.getAllAlumnos();
    }
    public Alumno getAlumnoPorId(int id){
        AlumnoJpaController alumnosController = new AlumnoJpaController(emf);
        return alumnosController.findAlumno(id);
    }
    public List<Alumno> buscarAlumnoPorNombre(String nombre){
        AlumnoJpaControllerExtended alumnosController = new AlumnoJpaControllerExtended(emf);
        return alumnosController.getAlumnoFromNombre(nombre);
    }
    
    public List<Alumno> buscarAlumnoPorCorreo(String correo){
        AlumnoJpaControllerExtended alumnosController = new AlumnoJpaControllerExtended(emf);
        return alumnosController.getAlumnoFromCorreo(correo);
    }
    
    public List<Alumno> buscarProximasReinscripciones(Date diaActual){
        Calendar cal = Calendar.getInstance();
        cal.setTime(diaActual);
        cal.add(Calendar.DATE, -5);
        
        Date inicio = cal.getTime();
        cal.add(Calendar.DATE, 10);
        Date fin = cal.getTime();
        
        AlumnoJpaControllerExtended alumnosController = new AlumnoJpaControllerExtended(emf);
        
        return alumnosController.getProximasReinscripciones(inicio, fin);
    }
    
    public List<Alumno> buscarProximasMensualidades(Date diaActual){
        Calendar cal = Calendar.getInstance();
        cal.setTime(diaActual);
        cal.add(Calendar.DATE, -5);
        
        Date inicio = cal.getTime();
        cal.add(Calendar.DATE, 10);
        Date fin = cal.getTime();
        
        AlumnoJpaControllerExtended alumnosController = new AlumnoJpaControllerExtended(emf);
        
        return alumnosController.getProximasMensualidades(inicio, fin);
    }
    
    public List<Alumno> visualizarRegistrosNoInscritosAGrupo(GrupoClase grupo){
        AlumnoJpaControllerExtended alumnosController = new AlumnoJpaControllerExtended(emf);
        return alumnosController.getAlumnosNoInscritosAGrupo(grupo);
    }

    public boolean inscribirAGrupo(Alumno a, GrupoClase grupoSeleccionado) {
        AlumnoJpaControllerExtended alumnosController = new AlumnoJpaControllerExtended(emf);
        
        a.getGrupoClaseList().add(grupoSeleccionado);
        a.setGrupoClaseList(a.getGrupoClaseList());
        
        try {
            alumnosController.edit(a);
        } catch (NonexistentEntityException ex) {
            return false;
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
