/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.GrupoClase;

/**
 *
 * @author AndrésRoberto
 */
public class GrupoClaseJpaControllerExtended extends GrupoClaseJpaController{
    
    public GrupoClaseJpaControllerExtended(EntityManagerFactory emf) {
        super(emf);
    }

    
    public List<GrupoClase> getAll(){
        EntityManager em = getEntityManager();
        List<GrupoClase> grupoClase = (List<GrupoClase>) em.createNamedQuery("GrupoClase.findAll").getResultList();
        em.close();
        return grupoClase;
    }
    
}
