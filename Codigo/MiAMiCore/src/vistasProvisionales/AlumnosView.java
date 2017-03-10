package vistasProvisionales;

import java.util.Date;
import modelo.Alumnos;
import recursos.AlumnoResource;

/**
 *
 * @author macbookpro
 */
public class AlumnosView {
    public static void main(String[] args){
        Alumnos alumno = new Alumnos();
        alumno.setNombre("Missael");
        alumno.setCorreo("Missael xp");
        alumno.setFechaNacimiento(new Date());
        alumno.setApellidos("Hernandez Rosado");
        alumno.setTelefono("1232323123");
        alumno.setTelefonoEmergencia("458739374");
        alumno.setTipoSangre("O+");
        alumno.setActivo(true);
        alumno.setDiapago(12);
        
        AlumnoResource recursoAlumno = new AlumnoResource();
        recursoAlumno.registrarAlumno(alumno);
    }
}
