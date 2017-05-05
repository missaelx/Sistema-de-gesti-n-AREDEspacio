package recursos;

import controladores.GrupoClaseJpaControllerExtended;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.GrupoClase;

/**
 *
 * @author Missael Hernandez
 */
public class GrupoClaseResource {
    EntityManagerFactory emf;
    public GrupoClaseResource(){
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
    }
    
    public List<GrupoClase> getClasesImpartidasPorMaestro(int idMaestro){
        GrupoClaseJpaControllerExtended controlador = new GrupoClaseJpaControllerExtended(emf);
        return controlador.getGruposFromMaestro(idMaestro);
    }
    
    public GrupoClase getGrupoClasePorId(Integer id){
        GrupoClaseJpaControllerExtended controlador = new GrupoClaseJpaControllerExtended(emf);
        return controlador.findGrupoClase(id);
    }
    
}
