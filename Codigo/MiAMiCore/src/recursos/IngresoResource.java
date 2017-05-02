/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import controladores.IngresoJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Ingreso;

/**
 *
 * @author Miguel Acosta
 */
public class IngresoResource {
    EntityManagerFactory emf;
    
    public IngresoResource(){
        this.emf = emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
    }
    
    public boolean registrarIngreso(Ingreso ingreso){
        IngresoJpaController controller = new IngresoJpaController(emf);
        controller.create(ingreso);
        return true;
    }
}
