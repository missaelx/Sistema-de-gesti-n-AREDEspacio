/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Ingreso;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Promociones;

/**
 *
 * @author macbookpro
 */
public class PromocionesJpaController implements Serializable {

    public PromocionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Promociones promociones) {
        if (promociones.getIngresoList() == null) {
            promociones.setIngresoList(new ArrayList<Ingreso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ingreso> attachedIngresoList = new ArrayList<Ingreso>();
            for (Ingreso ingresoListIngresoToAttach : promociones.getIngresoList()) {
                ingresoListIngresoToAttach = em.getReference(ingresoListIngresoToAttach.getClass(), ingresoListIngresoToAttach.getId());
                attachedIngresoList.add(ingresoListIngresoToAttach);
            }
            promociones.setIngresoList(attachedIngresoList);
            em.persist(promociones);
            for (Ingreso ingresoListIngreso : promociones.getIngresoList()) {
                Promociones oldIdPromocionOfIngresoListIngreso = ingresoListIngreso.getIdPromocion();
                ingresoListIngreso.setIdPromocion(promociones);
                ingresoListIngreso = em.merge(ingresoListIngreso);
                if (oldIdPromocionOfIngresoListIngreso != null) {
                    oldIdPromocionOfIngresoListIngreso.getIngresoList().remove(ingresoListIngreso);
                    oldIdPromocionOfIngresoListIngreso = em.merge(oldIdPromocionOfIngresoListIngreso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Promociones promociones) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Promociones persistentPromociones = em.find(Promociones.class, promociones.getId());
            List<Ingreso> ingresoListOld = persistentPromociones.getIngresoList();
            List<Ingreso> ingresoListNew = promociones.getIngresoList();
            List<String> illegalOrphanMessages = null;
            for (Ingreso ingresoListOldIngreso : ingresoListOld) {
                if (!ingresoListNew.contains(ingresoListOldIngreso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ingreso " + ingresoListOldIngreso + " since its idPromocion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Ingreso> attachedIngresoListNew = new ArrayList<Ingreso>();
            for (Ingreso ingresoListNewIngresoToAttach : ingresoListNew) {
                ingresoListNewIngresoToAttach = em.getReference(ingresoListNewIngresoToAttach.getClass(), ingresoListNewIngresoToAttach.getId());
                attachedIngresoListNew.add(ingresoListNewIngresoToAttach);
            }
            ingresoListNew = attachedIngresoListNew;
            promociones.setIngresoList(ingresoListNew);
            promociones = em.merge(promociones);
            for (Ingreso ingresoListNewIngreso : ingresoListNew) {
                if (!ingresoListOld.contains(ingresoListNewIngreso)) {
                    Promociones oldIdPromocionOfIngresoListNewIngreso = ingresoListNewIngreso.getIdPromocion();
                    ingresoListNewIngreso.setIdPromocion(promociones);
                    ingresoListNewIngreso = em.merge(ingresoListNewIngreso);
                    if (oldIdPromocionOfIngresoListNewIngreso != null && !oldIdPromocionOfIngresoListNewIngreso.equals(promociones)) {
                        oldIdPromocionOfIngresoListNewIngreso.getIngresoList().remove(ingresoListNewIngreso);
                        oldIdPromocionOfIngresoListNewIngreso = em.merge(oldIdPromocionOfIngresoListNewIngreso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = promociones.getId();
                if (findPromociones(id) == null) {
                    throw new NonexistentEntityException("The promociones with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Promociones promociones;
            try {
                promociones = em.getReference(Promociones.class, id);
                promociones.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The promociones with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ingreso> ingresoListOrphanCheck = promociones.getIngresoList();
            for (Ingreso ingresoListOrphanCheckIngreso : ingresoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Promociones (" + promociones + ") cannot be destroyed since the Ingreso " + ingresoListOrphanCheckIngreso + " in its ingresoList field has a non-nullable idPromocion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(promociones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Promociones> findPromocionesEntities() {
        return findPromocionesEntities(true, -1, -1);
    }

    public List<Promociones> findPromocionesEntities(int maxResults, int firstResult) {
        return findPromocionesEntities(false, maxResults, firstResult);
    }

    private List<Promociones> findPromocionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Promociones.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Promociones findPromociones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Promociones.class, id);
        } finally {
            em.close();
        }
    }

    public int getPromocionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Promociones> rt = cq.from(Promociones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
