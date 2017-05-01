package recursos;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Promociones;
import controladores.PromocionesJpaController;
import controladores.PromocionesJpaControllerExtended;
import controladores.MaestroJpaController;
import java.util.List;

/**
 *
 * @author Miguel Acosta
 */
public class  PromocionesResource {
    EntityManagerFactory emf;
    
    public PromocionesResource(){
        emf = Persistence.createEntityManagerFactory("MiAMiCorePU");
    }
    
    public boolean crearPromocion (Promociones promocion ){
        PromocionesJpaController promoController = new PromocionesJpaController(emf);
        boolean result =true;
        promoController.create(promocion);
        return result;
    }
    
    public boolean eliminarPromocion (Promociones promocion) throws Exception{
        promocion.setActivo(false);
        editarPromocion(promocion);
        return true;
    }
    
    public boolean editarPromocion(Promociones promocion) throws Exception{
        PromocionesJpaController promoController = new PromocionesJpaController(emf);
        promoController.edit(promocion);
        return true;
    }

    public List<Promociones> getActivos(){
        PromocionesJpaControllerExtended promoController = new PromocionesJpaControllerExtended(emf);
        List<Promociones> promos = promoController.getPromocionesActivas();
        return promos;
    }
    
    public List<Promociones> getAll(){
        
        PromocionesJpaController promoController = new PromocionesJpaController(emf);
        List<Promociones> promos = promoController.findPromocionesEntities();
        return promos;
    }
}
