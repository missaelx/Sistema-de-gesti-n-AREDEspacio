package recursos;

import controladores.MaestroJpaController;
import controladores.MaestroJpaControllerExtended;
import controladores.PagodesalarioJpaController;
import controladores.PagodesalarioJpaControllerExtended;
import controladores.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Maestro;
import modelo.Pagodesalario;

/**
 *
 * @author 
 */
public class MaestroResource {
    EntityManagerFactory emf;
    public MaestroResource(){
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
    }
    public boolean registrarMaestro(Maestro maestro){
        boolean result = true;
        MaestroJpaController maestrosController = new MaestroJpaController(emf);
        maestrosController.create(maestro);
        return result;
    }
    public boolean eliminarMaestro(Maestro maestro) throws NonexistentEntityException{
        boolean result = true;
        MaestroJpaController maestrosController = new MaestroJpaController(emf);
        maestro.setActivo(false);
        result = modificarMaestro(maestro);
        return result;
    }
    public boolean modificarMaestro(Maestro maestro) throws NonexistentEntityException{
        boolean result = true;
        MaestroJpaController maestrosController = new MaestroJpaController(emf);
        try {
            maestrosController.edit(maestro);
        } catch (Exception ex) {
            result = false;
            Logger.getLogger(MaestroResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public Maestro buscarMaestroPorIdentificador(int id){
        MaestroJpaController maestrosController = new MaestroJpaController(emf);
        return maestrosController.findMaestro(id);
    }
    
    public List<Maestro> buscarMaestroPorNombre(String nombre){
        MaestroJpaControllerExtended MaestroController = new MaestroJpaControllerExtended(emf);
        return MaestroController.getMaestroFromNombre(nombre);
    }
    
    public List<Pagodesalario> getPagosDeSalario(int idMaestro){
        PagodesalarioJpaControllerExtended salarioController = new PagodesalarioJpaControllerExtended(emf);
        
        return salarioController.getPagoSalarioFromMaestro(idMaestro);
    }
    public List<Maestro> visualizarRegistros(){
        MaestroJpaControllerExtended maestroController = new MaestroJpaControllerExtended(emf);
        return maestroController.getAllMaestros();
    }
    public List<Maestro> getMaestros(){
        MaestroJpaControllerExtended maestrosController = new MaestroJpaControllerExtended(emf);
        return maestrosController.getAllMaestros();
    }
    
}
