package recursos;

import controladores.GastovariableJpaController;
import controladores.PagodesalarioJpaController;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Gastovariable;
import modelo.Pagodesalario;

/**
 *
 * @author Missael Hernandez Rosado
 */
public class EgresosResource {
    private EntityManagerFactory emf;
    
    public EgresosResource(){
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
    }
    
    public List<Gastovariable> getEgresosVariables(){
        GastovariableJpaController controlador = new GastovariableJpaController(emf);
        return controlador.findGastovariableEntities();
    }
    
    public List<Pagodesalario> getPagosSalariales(){
        PagodesalarioJpaController controlador = new PagodesalarioJpaController(emf);
        return controlador.findPagodesalarioEntities();
    }
}
