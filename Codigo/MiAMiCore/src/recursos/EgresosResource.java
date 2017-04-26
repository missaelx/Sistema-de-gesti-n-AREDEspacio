package recursos;

import controladores.EgresoJpaController;
import controladores.GastovariableJpaController;
import controladores.PagodesalarioJpaController;
import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
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
    
    public boolean registrarEgreso(Gastovariable pago){
        boolean resultado = true;
        
        GastovariableJpaController controlador = new GastovariableJpaController(emf);
        EgresoJpaController controladorEgreso = new EgresoJpaController(emf);
        
        Egreso egreso = pago.getIdEgreso();
        try{
            controladorEgreso.create(egreso);
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
    
    public boolean eliminarPagoSalario(Pagodesalario pago){
        Egreso egreso = pago.getIdegreso();
        PagodesalarioJpaController controladorSalario = new PagodesalarioJpaController(emf);
        EgresoJpaController controladorEgreso = new EgresoJpaController(emf);
        
        boolean result = true;
        
        try{
            controladorSalario.destroy(pago.getId());
            pago.setIdegreso(null);
            egreso.setPagodesalario(null);
            controladorEgreso.destroy(egreso.getId());
        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            System.out.println(ex.getMessage());
            result = false;
        }
        return result;
    }

    public boolean eliminarEgreso(Gastovariable pago) {
        Egreso egreso = pago.getIdEgreso();
        GastovariableJpaController controladorGasto = new GastovariableJpaController(emf);
        EgresoJpaController controladorEgreso = new EgresoJpaController(emf);
        
        boolean result = true;
        
        try{
            controladorGasto.destroy(pago.getId());
            pago.setIdEgreso(null);
            egreso.setPagodesalario(null);
            controladorEgreso.destroy(egreso.getId());
        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            System.out.println(ex.getMessage());
            result = false;
        }
        return result;
    }
}
