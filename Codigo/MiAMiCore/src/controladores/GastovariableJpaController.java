/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Egreso;
import modelo.Gastovariable;

/**
 *
 * @author macbookpro
 */
public class GastovariableJpaController implements Serializable {

    public GastovariableJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gastovariable gastovariable) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Egreso idEgreso = gastovariable.getIdEgreso();
            if (idEgreso != null) {
                idEgreso = em.getReference(idEgreso.getClass(), idEgreso.getId());
                gastovariable.setIdEgreso(idEgreso);
            }
            em.persist(gastovariable);
            if (idEgreso != null) {
                idEgreso.getGastovariableList().add(gastovariable);
                idEgreso = em.merge(idEgreso);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGastovariable(gastovariable.getId()) != null) {
                throw new PreexistingEntityException("Gastovariable " + gastovariable + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Gastovariable gastovariable) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gastovariable persistentGastovariable = em.find(Gastovariable.class, gastovariable.getId());
            Egreso idEgresoOld = persistentGastovariable.getIdEgreso();
            Egreso idEgresoNew = gastovariable.getIdEgreso();
            if (idEgresoNew != null) {
                idEgresoNew = em.getReference(idEgresoNew.getClass(), idEgresoNew.getId());
                gastovariable.setIdEgreso(idEgresoNew);
            }
            gastovariable = em.merge(gastovariable);
            if (idEgresoOld != null && !idEgresoOld.equals(idEgresoNew)) {
                idEgresoOld.getGastovariableList().remove(gastovariable);
                idEgresoOld = em.merge(idEgresoOld);
            }
            if (idEgresoNew != null && !idEgresoNew.equals(idEgresoOld)) {
                idEgresoNew.getGastovariableList().add(gastovariable);
                idEgresoNew = em.merge(idEgresoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gastovariable.getId();
                if (findGastovariable(id) == null) {
                    throw new NonexistentEntityException("The gastovariable with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gastovariable gastovariable;
            try {
                gastovariable = em.getReference(Gastovariable.class, id);
                gastovariable.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gastovariable with id " + id + " no longer exists.", enfe);
            }
            Egreso idEgreso = gastovariable.getIdEgreso();
            if (idEgreso != null) {
                idEgreso.getGastovariableList().remove(gastovariable);
                idEgreso = em.merge(idEgreso);
            }
            em.remove(gastovariable);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Gastovariable> findGastovariableEntities() {
        return findGastovariableEntities(true, -1, -1);
    }

    public List<Gastovariable> findGastovariableEntities(int maxResults, int firstResult) {
        return findGastovariableEntities(false, maxResults, firstResult);
    }

    private List<Gastovariable> findGastovariableEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gastovariable.class));
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

    public Gastovariable findGastovariable(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gastovariable.class, id);
        } finally {
            em.close();
        }
    }

    public int getGastovariableCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gastovariable> rt = cq.from(Gastovariable.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
