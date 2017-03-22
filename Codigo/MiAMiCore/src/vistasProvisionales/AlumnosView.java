package vistasProvisionales;

import java.util.List;
import modelo.Pagodesalario;
import recursos.MaestroResource;


/**
 *
 * @author macbookpro
 */
public class AlumnosView {
    public static void main(String[] args){
//        Alumnos alumno = new Alumnos();
//        alumno.setIdalumno(1);
//        alumno.setNombre("Missael");
//        alumno.setCorreo("Missael xp");
//        alumno.setFechaNacimiento(new Date());
//        alumno.setApellidos("Hernandez Rosado");
//        alumno.setTelefono("1232323123");
//        alumno.setTelefonoEmergencia("458739374");
//        alumno.setTipoSangre("O+");
//        alumno.setActivo(true);
//        alumno.setDiapago(12);
//        
//        AlumnoResource recursoAlumno = new AlumnoResource();
//        try {
//            //recursoAlumno.registrarAlumno(alumno);
//            recursoAlumno.eliminarAlumno(alumno);
//        } catch (NonexistentEntityException ex) {
//            System.out.println("No existe la entidad");
//        } catch (Exception ex) {
//            Logger.getLogger(AlumnosView.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        MaestroResource mResource = new MaestroResource();
        List<Pagodesalario> pagos = mResource.getPagosDeSalario(1);
        for(Pagodesalario e : pagos){
            System.out.println(e.toString());
        }
    }
}
