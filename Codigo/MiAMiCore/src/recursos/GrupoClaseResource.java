package recursos;

import controladores.GrupoClaseJpaControllerExtended;
import controladores.HorarioJpaControllerExtended;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.GrupoClase;
import modelo.Horario;

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
    
    public List<GrupoClase> getClasesFromDia(int weekDay){
        String dia = "";
        switch(weekDay){
            case 1:
                dia = "lun";
                break;
            case 2:
                dia = "mar";
                break;
            case 3:
                dia = "mie";
                break;
            case 4:
                dia = "jue";
                break;
            case 5:
                dia = "vie";
                break;
            case 6:
                dia = "sab";
                break;
        }
        HorarioJpaControllerExtended controladorHorario = new HorarioJpaControllerExtended(emf);
        List<Horario> horariosEncontrados =  controladorHorario.getHorariosFromDia(dia);
        List<GrupoClase> clases = new ArrayList<>();
        
        for(Horario h: horariosEncontrados){
            clases.add(h.getIdGrupoClase());
        }
        return clases;
    }
}
