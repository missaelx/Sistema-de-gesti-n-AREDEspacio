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
import modelo.Egreso;
import modelo.Maestro;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Pagodesalario;

/**
 *
 * @author macbookpro
 */
public class PagodesalarioJpaController implements Serializable {

    public PagodesalarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pagodesalario pagodesalario) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Egreso idegresoOrphanCheck = pagodesalario.getIdegreso();
        if (idegresoOrphanCheck != null) {
            Pagodesalario oldPagodesalarioOfIdegreso = idegresoOrphanCheck.getPagodesalario();
            if (oldPagodesalarioOfIdegreso != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Egreso " + idegresoOrphanCheck + " already has an item of type Pagodesalario whose idegreso column cannot be null. Please make another selection for the idegreso field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Egreso idegreso = pagodesalario.getIdegreso();
            if (idegreso != null) {
                idegreso = em.getReference(idegreso.getClass(), idegreso.getId());
                pagodesalario.setIdegreso(idegreso);
            }
            Maestro idmaestro = pagodesalario.getIdmaestro();
            if (idmaestro != null) {
                idmaestro = em.getReference(idmaestro.getClass(), idmaestro.getId());
                pagodesalario.setIdmaestro(idmaestro);
            }
            em.persist(pagodesalario);
            if (idegreso != null) {
                idegreso.setPagodesalario(pagodesalario);
                idegreso = em.merge(idegreso);
            }
            if (idmaestro != null) {
                idmaestro.getPagodesalarioList().add(pagodesalario);
                idmaestro = em.merge(idmaestro);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pagodesalario pagodesalario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagodesalario persistentPagodesalario = em.find(Pagodesalario.class, pagodesalario.getId());
            Egreso idegresoOld = persistentPagodesalario.getIdegreso();
            Egreso idegresoNew = pagodesalario.getIdegreso();
            Maestro idmaestroOld = persistentPagodesalario.getIdmaestro();
            Maestro idmaestroNew = pagodesalario.getIdmaestro();
            List<String> illegalOrphanMessages = null;
            if (idegresoNew != null && !idegresoNew.equals(idegresoOld)) {
                Pagodesalario oldPagodesalarioOfIdegreso = idegresoNew.getPagodesalario();
                if (oldPagodesalarioOfIdegreso != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Egreso " + idegresoNew + " already has an item of type Pagodesalario whose idegreso column cannot be null. Please make another selection for the idegreso field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idegresoNew != null) {
                idegresoNew = em.getReference(idegresoNew.getClass(), idegresoNew.getId());
                pagodesalario.setIdegreso(idegresoNew);
            }
            if (idmaestroNew != null) {
                idmaestroNew = em.getReference(idmaestroNew.getClass(), idmaestroNew.getId());
                pagodesalario.setIdmaestro(idmaestroNew);
            }
            pagodesalario = em.merge(pagodesalario);
            if (idegresoOld != null && !idegresoOld.equals(idegresoNew)) {
                idegresoOld.setPagodesalario(null);
                idegresoOld = em.merge(idegresoOld);
            }
            if (idegresoNew != null && !idegresoNew.equals(idegresoOld)) {
                idegresoNew.setPagodesalario(pagodesalario);
                idegresoNew = em.merge(idegresoNew);
            }
            if (idmaestroOld != null && !idmaestroOld.equals(idmaestroNew)) {
                idmaestroOld.getPagodesalarioList().remove(pagodesalario);
                idmaestroOld = em.merge(idmaestroOld);
            }
            if (idmaestroNew != null && !idmaestroNew.equals(idmaestroOld)) {
                idmaestroNew.getPagodesalarioList().add(pagodesalario);
                idmaestroNew = em.merge(idmaestroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagodesalario.getId();
                if (findPagodesalario(id) == null) {
                    throw new NonexistentEntityException("The pagodesalario with id " + id + " no longer exists.");
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
            Pagodesalario pagodesalario;
            try {
                pagodesalario = em.getReference(Pagodesalario.class, id);
                pagodesalario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagodesalario with id " + id + " no longer exists.", enfe);
            }
            Egreso idegreso = pagodesalario.getIdegreso();
            if (idegreso != null) {
                idegreso.setPagodesalario(null);
                idegreso = em.merge(idegreso);
            }
            Maestro idmaestro = pagodesalario.getIdmaestro();
            if (idmaestro != null) {
                idmaestro.getPagodesalarioList().remove(pagodesalario);
                idmaestro = em.merge(idmaestro);
            }
            em.remove(pagodesalario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pagodesalario> findPagodesalarioEntities() {
        return findPagodesalarioEntities(true, -1, -1);
    }

    public List<Pagodesalario> findPagodesalarioEntities(int maxResults, int firstResult) {
        return findPagodesalarioEntities(false, maxResults, firstResult);
    }

    private List<Pagodesalario> findPagodesalarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pagodesalario.class));
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

    public Pagodesalario findPagodesalario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pagodesalario.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagodesalarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pagodesalario> rt = cq.from(Pagodesalario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
