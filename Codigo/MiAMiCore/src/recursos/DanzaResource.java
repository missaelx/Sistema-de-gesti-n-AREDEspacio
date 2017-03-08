package recursos;

import modelo.GrupoClase;
import modelo.TipoDanza;

/**
 *
 * @author macbookpro
 */
public class DanzaResource {
    public boolean crearDanza(TipoDanza tipoDanza){
        return true;
    }
    
    public boolean crearGrupoClase(GrupoClase grupo){
        return true;
    }
    
    
    
    
    public boolean modificarGrupoClase(GrupoClase grupo){
        throw new UnsupportedOperationException();
    }
    public boolean eliminarGrupoClase(GrupoClase grupo){
        throw new UnsupportedOperationException();
    }
    public boolean eliminarDanza(TipoDanza tipoDanza){
        throw new UnsupportedOperationException();
    }
}
