package controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Pagodesalario;

/**
 *
 * @author macbookpro
 */
public class MaestroJpaControllerExtended extends MaestroJpaController{
    
    public MaestroJpaControllerExtended(EntityManagerFactory emf) {
        super(emf);
    }
    

    
}
