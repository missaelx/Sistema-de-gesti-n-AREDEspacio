package recursos;

import controladores.EgresoJpaController;
import controladores.GastovariableJpaController;
import controladores.PagodesalarioJpaController;
import controladores.exceptions.IllegalOrphanException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Egreso;
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
    
    public boolean registrarPagoSalario(Pagodesalario pago){
        Egreso egreso = pago.getIdegreso();
        PagodesalarioJpaController controladorSalario = new PagodesalarioJpaController(emf);
        EgresoJpaController controladorEgreso = new EgresoJpaController(emf);
        controladorEgreso.create(egreso);
        boolean resultado = true;
        try {
            controladorSalario.create(pago);
        } catch (IllegalOrphanException ex) {
            resultado = false;
        }
        
        return resultado;
    }
}
