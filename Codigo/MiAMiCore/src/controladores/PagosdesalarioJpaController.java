/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Egresos;
import modelo.Maestros;
import modelo.Pagosdesalario;

/**
 *
 * @author macbookpro
 */
public class PagosdesalarioJpaController implements Serializable {

    public PagosdesalarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pagosdesalario pagosdesalario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Egresos idegreso = pagosdesalario.getIdegreso();
            if (idegreso != null) {
                idegreso = em.getReference(idegreso.getClass(), idegreso.getIdegresos());
                pagosdesalario.setIdegreso(idegreso);
            }
            Maestros idmaestro = pagosdesalario.getIdmaestro();
            if (idmaestro != null) {
                idmaestro = em.getReference(idmaestro.getClass(), idmaestro.getIdmaestro());
                pagosdesalario.setIdmaestro(idmaestro);
            }
            em.persist(pagosdesalario);
            if (idegreso != null) {
                idegreso.getPagosdesalarioList().add(pagosdesalario);
                idegreso = em.merge(idegreso);
            }
            if (idmaestro != null) {
                idmaestro.getPagosdesalarioList().add(pagosdesalario);
                idmaestro = em.merge(idmaestro);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pagosdesalario pagosdesalario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagosdesalario persistentPagosdesalario = em.find(Pagosdesalario.class, pagosdesalario.getIdpagodesalario());
            Egresos idegresoOld = persistentPagosdesalario.getIdegreso();
            Egresos idegresoNew = pagosdesalario.getIdegreso();
            Maestros idmaestroOld = persistentPagosdesalario.getIdmaestro();
            Maestros idmaestroNew = pagosdesalario.getIdmaestro();
            if (idegresoNew != null) {
                idegresoNew = em.getReference(idegresoNew.getClass(), idegresoNew.getIdegresos());
                pagosdesalario.setIdegreso(idegresoNew);
            }
            if (idmaestroNew != null) {
                idmaestroNew = em.getReference(idmaestroNew.getClass(), idmaestroNew.getIdmaestro());
                pagosdesalario.setIdmaestro(idmaestroNew);
            }
            pagosdesalario = em.merge(pagosdesalario);
            if (idegresoOld != null && !idegresoOld.equals(idegresoNew)) {
                idegresoOld.getPagosdesalarioList().remove(pagosdesalario);
                idegresoOld = em.merge(idegresoOld);
            }
            if (idegresoNew != null && !idegresoNew.equals(idegresoOld)) {
                idegresoNew.getPagosdesalarioList().add(pagosdesalario);
                idegresoNew = em.merge(idegresoNew);
            }
            if (idmaestroOld != null && !idmaestroOld.equals(idmaestroNew)) {
                idmaestroOld.getPagosdesalarioList().remove(pagosdesalario);
                idmaestroOld = em.merge(idmaestroOld);
            }
            if (idmaestroNew != null && !idmaestroNew.equals(idmaestroOld)) {
                idmaestroNew.getPagosdesalarioList().add(pagosdesalario);
                idmaestroNew = em.merge(idmaestroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagosdesalario.getIdpagodesalario();
                if (findPagosdesalario(id) == null) {
                    throw new NonexistentEntityException("The pagosdesalario with id " + id + " no longer exists.");
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
            Pagosdesalario pagosdesalario;
            try {
                pagosdesalario = em.getReference(Pagosdesalario.class, id);
                pagosdesalario.getIdpagodesalario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagosdesalario with id " + id + " no longer exists.", enfe);
            }
            Egresos idegreso = pagosdesalario.getIdegreso();
            if (idegreso != null) {
                idegreso.getPagosdesalarioList().remove(pagosdesalario);
                idegreso = em.merge(idegreso);
            }
            Maestros idmaestro = pagosdesalario.getIdmaestro();
            if (idmaestro != null) {
                idmaestro.getPagosdesalarioList().remove(pagosdesalario);
                idmaestro = em.merge(idmaestro);
            }
            em.remove(pagosdesalario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pagosdesalario> findPagosdesalarioEntities() {
        return findPagosdesalarioEntities(true, -1, -1);
    }

    public List<Pagosdesalario> findPagosdesalarioEntities(int maxResults, int firstResult) {
        return findPagosdesalarioEntities(false, maxResults, firstResult);
    }

    private List<Pagosdesalario> findPagosdesalarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pagosdesalario.class));
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

    public Pagosdesalario findPagosdesalario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pagosdesalario.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagosdesalarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pagosdesalario> rt = cq.from(Pagosdesalario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
