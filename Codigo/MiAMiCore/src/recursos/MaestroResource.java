package recursos;

import controladores.MaestrosJpaController;
import controladores.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Alumnos;
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
    
    
    public List<Pagosdesalario> getPagosDeSalario(){
        return new ArrayList<Pagosdesalario>();
    }
    
    
}
