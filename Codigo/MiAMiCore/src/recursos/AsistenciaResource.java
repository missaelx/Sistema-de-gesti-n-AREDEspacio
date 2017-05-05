/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import controladores.AsistenciaJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Asistencia;

/**
 *
 * @author Miguel Acosta
 */
public class AsistenciaResource {
    
    EntityManagerFactory emf;
    public AsistenciaResource(){
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
    }
    
    public boolean registrarAsistencia(Asistencia asistencia){
        AsistenciaJpaController control = new AsistenciaJpaController(emf);
        control.create(asistencia);
        return true;
    }
}
