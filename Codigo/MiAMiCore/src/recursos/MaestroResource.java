package recursos;

import controladores.MaestrosJpaController;
import controladores.PagosdesalarioJpaController;
import controladores.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Maestros;
import modelo.Pagosdesalario;

/**
 *
 * @author 
 */
public class MaestroResource {
    EntityManagerFactory emf;
    public MaestroResource(){
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
    }
    public boolean registrarMaestro(Maestros maestro){
        boolean result = true;
        MaestrosJpaController maestrosController = new MaestrosJpaController(emf);
        maestrosController.create(maestro);
        return result;
    }
    public boolean eliminarMaestro(Maestros maestro) throws NonexistentEntityException{
        boolean result = true;
        MaestrosJpaController maestrosController = new MaestrosJpaController(emf);
        maestro.setActivo(false);
        result = modificarMaestro(maestro);
        return result;
    }
    public boolean modificarMaestro(Maestros maestro) throws NonexistentEntityException{
        boolean result = true;
        MaestrosJpaController maestrosController = new MaestrosJpaController(emf);
        try {
            maestrosController.edit(maestro);
        } catch (Exception ex) {
            result = false;
            Logger.getLogger(MaestroResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public Maestros buscarMaestroPorIdentificador(int id){
        MaestrosJpaController maestrosController = new MaestrosJpaController(emf);
        return maestrosController.findMaestros(id);
    }
    
    
    public List<Pagosdesalario> getPagosDeSalario(int idMaestro){
        PagosdesalarioJpaController salarioController = new PagosdesalarioJpaController(emf);
        
        return salarioController.getPagosSalarioFromMaestro(idMaestro);
    }
    
    public List<Maestros> getMaestros(){
        MaestrosJpaController maestrosController = new MaestrosJpaController(emf);
        return maestrosController.findMaestrosEntities();
    }
    
}
