package recursos;

import controladores.AlumnoJpaController;
import controladores.EgresoJpaController;
import controladores.GastovariableJpaController;
import controladores.GastovariableJpaControllerExtended;
import controladores.IngresoJpaController;
import controladores.InscripcionJpaController;
import controladores.InscripcionJpaControllerExtended;
import controladores.MensualidadJpaController;
import controladores.MensualidadJpaControllerExtended;
import controladores.PagodesalarioJpaController;
import controladores.PagodesalarioJpaControllerExtended;
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
import modelo.Egreso;
import modelo.Gastovariable;
import modelo.Ingreso;
import modelo.Inscripcion;
import modelo.Mensualidad;
import modelo.Pagodesalario;

/**
 *
 * @author macbookpro
 */
public class IngresosResource {
    private EntityManagerFactory emf;
    
    public IngresosResource(){
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
    }
    
    public List<Inscripcion> getInscripciones(){
        InscripcionJpaController controlador = new InscripcionJpaController(emf);
        return controlador.findInscripcionEntities();
    }
    
    public List<Mensualidad> getMensualidades(){
        MensualidadJpaController controlador = new MensualidadJpaController(emf);
        return controlador.findMensualidadEntities();
    }
    
    public boolean registrarPagoMensualidad(Mensualidad pago){
        Ingreso ingreso = pago.getIdingreso();
        MensualidadJpaController controladorMensualidad = new MensualidadJpaController(emf);
        IngresoJpaController controladorIngreso = new IngresoJpaController(emf);
        controladorIngreso.create(ingreso);
        
        boolean resultado = true;
        
        controladorMensualidad.create(pago);
        
        //actualizamos o creamos la fecha de pago de mensualidad del alumno
        AlumnoResource recursoAlumno = new AlumnoResource();
        Alumno alumnoQuePago = pago.getIdalumno();
        
        Calendar cal = Calendar.getInstance();
        
        if(alumnoQuePago.getDiapago() != null){
            cal.setTime(alumnoQuePago.getDiapago());
        } else {
            cal.setTime(new Date());
        }
        
        cal.add(Calendar.MONTH, 1);
        alumnoQuePago.setDiapago(cal.getTime());
        try {
            recursoAlumno.modificarAlumno(alumnoQuePago);
        } catch (Exception ex) {
            return false;
        }
        
        return resultado;
    }
    
    public boolean registrarInscripcion(Inscripcion pago){
        boolean resultado = true;
        
        InscripcionJpaController controlador = new InscripcionJpaController(emf);
        IngresoJpaController controladorIngreso = new IngresoJpaController(emf);
        AlumnoJpaController controladorAlumno = new AlumnoJpaController(emf);
        
        
        Ingreso ingreso = pago.getIdingreso();
        Alumno alumno = pago.getIdalumno();
        
        Calendar c = Calendar.getInstance();
        Date dateSeteada = ingreso.getFecha();
        
        if(alumno.getFechaInscripcion() == null){
            c.setTime(dateSeteada);
        } else {
            c.setTime(alumno.getFechaInscripcion());
        }
        
        c.add(Calendar.YEAR, 1);
        alumno.setFechaInscripcion(c.getTime());
        
        try{
            controladorAlumno.edit(alumno);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(IngresosResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(IngresosResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        try{
            controladorIngreso.create(ingreso);
        } catch(Exception e){
            System.out.println("Linea 62");
            System.out.println(e.getMessage());
        }
        try {
            controlador.create(pago);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            resultado = false;
        }
        
        
        return resultado;
    }
    
    public boolean eliminarMensualidad(Mensualidad pago){
        Ingreso ingreso = pago.getIdingreso();
        MensualidadJpaController controladorMensualidad = new MensualidadJpaController(emf);
        IngresoJpaController controladorIngreso = new IngresoJpaController(emf);
        
        boolean result = true;
        
        try{
            controladorMensualidad.destroy(pago.getId());
            pago.setIdingreso(null);
            ingreso.setMensualidadList(null);
            controladorIngreso.destroy(ingreso.getId());
        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            System.out.println(ex.getMessage());
            result = false;
        }
        return result;
    }

    public boolean eliminarInscripcion(Inscripcion pago) {
        Ingreso ingreso = pago.getIdingreso();
        InscripcionJpaController controladorInscripcion = new InscripcionJpaController(emf);
        IngresoJpaController controladorIngreso = new IngresoJpaController(emf);
        
        boolean result = true;
        
        try{
            controladorInscripcion.destroy(pago.getId());
            pago.setIdingreso(null);
            ingreso.setInscripcionList(null);
            controladorIngreso.destroy(ingreso.getId());
        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            System.out.println(ex.getMessage());
            result = false;
        }
        return result;
    }
    
    public List<Inscripcion> getInscripcionesEntreFechas(Date inicio, Date fin){
        InscripcionJpaControllerExtended controlador = new InscripcionJpaControllerExtended(emf);
        return controlador.getInscripcionesPorFechas(inicio, fin);
    }
    
    public List<Mensualidad> getMensualidadesEntreFechas(Date inicio, Date fin){
        MensualidadJpaControllerExtended controlador = new MensualidadJpaControllerExtended(emf);
        return controlador.getMensualidadesEntreFechas(inicio, fin);
    }
    
    public boolean registrarIngreso(Ingreso ingreso){
        IngresoJpaController controller = new IngresoJpaController(emf);
        controller.create(ingreso);
        return true;
    }
}
