package recursos;

import controladores.GrupoClaseJpaController;
import controladores.TipoDanzaJpaController;
import controladores.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.GrupoClase;
import modelo.TipoDanza;

/**
 *
 * @author macbookpro
 */
public class DanzaResource {
    EntityManagerFactory emf;
    public DanzaResource(){
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
    }
    
    public boolean crearDanza(TipoDanza tipoDanza){
        TipoDanzaJpaController danzaController = new TipoDanzaJpaController(emf);
        danzaController.create(tipoDanza);
        return true;
    }
    
    public boolean modificarDanza(TipoDanza tipoDanza) throws NonexistentEntityException{
        TipoDanzaJpaController danzaController = new TipoDanzaJpaController(emf);
        try {
            danzaController.edit(tipoDanza);
        } catch (Exception ex) {
            Logger.getLogger(DanzaResource.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    public boolean eliminarDanza(TipoDanza tipoDanza) throws NonexistentEntityException{
        boolean resultado = true;
        TipoDanzaJpaController maestroController = new TipoDanzaJpaController(emf);
        tipoDanza.setActivo(false);
        modificarDanza(tipoDanza);
        return resultado;
    }
    
    public List<TipoDanza> getTiposDanza(){
        TipoDanzaJpaController danzaController = new TipoDanzaJpaController(emf);
        return danzaController.findTipoDanzaEntities();
    }
    
    public List<TipoDanza> visualizarRegistros() {
        TipoDanzaJpaController tipoDanzasController = new TipoDanzaJpaController(emf);
        return tipoDanzasController.getAllTipoDanzas();
    }
    
    
    
    public boolean crearGrupoClase(GrupoClase grupo){
        GrupoClaseJpaController grupoController = new GrupoClaseJpaController(emf);
        grupoController.create(grupo);
        return true;
    }
    
    public boolean modificarGrupoClase(GrupoClase grupo) throws NonexistentEntityException{
        GrupoClaseJpaController grupoController = new GrupoClaseJpaController(emf);
        try {
            grupoController.edit(grupo);
        } catch (Exception ex) {
            Logger.getLogger(DanzaResource.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    public boolean eliminarGrupoClase(GrupoClase grupo) throws NonexistentEntityException{
        grupo.setActivo(false);
        return modificarGrupoClase(grupo);
    }
    
    public List<GrupoClase> getGruposClase(){
        GrupoClaseJpaController grupoController = new GrupoClaseJpaController(emf);
        return grupoController.findGrupoClaseEntities();
    }
    public GrupoClase getGrupoClase(int idGrupo){
        GrupoClaseJpaController grupoController = new GrupoClaseJpaController(emf);
        return grupoController.findGrupoClase(idGrupo);
    }
    
}
