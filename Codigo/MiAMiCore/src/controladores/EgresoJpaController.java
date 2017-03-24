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
import modelo.Pagodesalario;
import modelo.Gastovariable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Egreso;

/**
 *
 * @author macbookpro
 */
public class EgresoJpaController implements Serializable {

    public EgresoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Egreso egreso) {
        if (egreso.getGastovariableList() == null) {
            egreso.setGastovariableList(new ArrayList<Gastovariable>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagodesalario pagodesalario = egreso.getPagodesalario();
            if (pagodesalario != null) {
                pagodesalario = em.getReference(pagodesalario.getClass(), pagodesalario.getId());
                egreso.setPagodesalario(pagodesalario);
            }
            List<Gastovariable> attachedGastovariableList = new ArrayList<Gastovariable>();
            for (Gastovariable gastovariableListGastovariableToAttach : egreso.getGastovariableList()) {
                gastovariableListGastovariableToAttach = em.getReference(gastovariableListGastovariableToAttach.getClass(), gastovariableListGastovariableToAttach.getId());
                attachedGastovariableList.add(gastovariableListGastovariableToAttach);
            }
            egreso.setGastovariableList(attachedGastovariableList);
            em.persist(egreso);
            if (pagodesalario != null) {
                Egreso oldIdegresoOfPagodesalario = pagodesalario.getIdegreso();
                if (oldIdegresoOfPagodesalario != null) {
                    oldIdegresoOfPagodesalario.setPagodesalario(null);
                    oldIdegresoOfPagodesalario = em.merge(oldIdegresoOfPagodesalario);
                }
                pagodesalario.setIdegreso(egreso);
                pagodesalario = em.merge(pagodesalario);
            }
            for (Gastovariable gastovariableListGastovariable : egreso.getGastovariableList()) {
                Egreso oldIdEgresoOfGastovariableListGastovariable = gastovariableListGastovariable.getIdEgreso();
                gastovariableListGastovariable.setIdEgreso(egreso);
                gastovariableListGastovariable = em.merge(gastovariableListGastovariable);
                if (oldIdEgresoOfGastovariableListGastovariable != null) {
                    oldIdEgresoOfGastovariableListGastovariable.getGastovariableList().remove(gastovariableListGastovariable);
                    oldIdEgresoOfGastovariableListGastovariable = em.merge(oldIdEgresoOfGastovariableListGastovariable);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Egreso egreso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Egreso persistentEgreso = em.find(Egreso.class, egreso.getId());
            Pagodesalario pagodesalarioOld = persistentEgreso.getPagodesalario();
            Pagodesalario pagodesalarioNew = egreso.getPagodesalario();
            List<Gastovariable> gastovariableListOld = persistentEgreso.getGastovariableList();
            List<Gastovariable> gastovariableListNew = egreso.getGastovariableList();
            List<String> illegalOrphanMessages = null;
            if (pagodesalarioOld != null && !pagodesalarioOld.equals(pagodesalarioNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Pagodesalario " + pagodesalarioOld + " since its idegreso field is not nullable.");
            }
            for (Gastovariable gastovariableListOldGastovariable : gastovariableListOld) {
                if (!gastovariableListNew.contains(gastovariableListOldGastovariable)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Gastovariable " + gastovariableListOldGastovariable + " since its idEgreso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pagodesalarioNew != null) {
                pagodesalarioNew = em.getReference(pagodesalarioNew.getClass(), pagodesalarioNew.getId());
                egreso.setPagodesalario(pagodesalarioNew);
            }
            List<Gastovariable> attachedGastovariableListNew = new ArrayList<Gastovariable>();
            for (Gastovariable gastovariableListNewGastovariableToAttach : gastovariableListNew) {
                gastovariableListNewGastovariableToAttach = em.getReference(gastovariableListNewGastovariableToAttach.getClass(), gastovariableListNewGastovariableToAttach.getId());
                attachedGastovariableListNew.add(gastovariableListNewGastovariableToAttach);
            }
            gastovariableListNew = attachedGastovariableListNew;
            egreso.setGastovariableList(gastovariableListNew);
            egreso = em.merge(egreso);
            if (pagodesalarioNew != null && !pagodesalarioNew.equals(pagodesalarioOld)) {
                Egreso oldIdegresoOfPagodesalario = pagodesalarioNew.getIdegreso();
                if (oldIdegresoOfPagodesalario != null) {
                    oldIdegresoOfPagodesalario.setPagodesalario(null);
                    oldIdegresoOfPagodesalario = em.merge(oldIdegresoOfPagodesalario);
                }
                pagodesalarioNew.setIdegreso(egreso);
                pagodesalarioNew = em.merge(pagodesalarioNew);
            }
            for (Gastovariable gastovariableListNewGastovariable : gastovariableListNew) {
                if (!gastovariableListOld.contains(gastovariableListNewGastovariable)) {
                    Egreso oldIdEgresoOfGastovariableListNewGastovariable = gastovariableListNewGastovariable.getIdEgreso();
                    gastovariableListNewGastovariable.setIdEgreso(egreso);
                    gastovariableListNewGastovariable = em.merge(gastovariableListNewGastovariable);
                    if (oldIdEgresoOfGastovariableListNewGastovariable != null && !oldIdEgresoOfGastovariableListNewGastovariable.equals(egreso)) {
                        oldIdEgresoOfGastovariableListNewGastovariable.getGastovariableList().remove(gastovariableListNewGastovariable);
                        oldIdEgresoOfGastovariableListNewGastovariable = em.merge(oldIdEgresoOfGastovariableListNewGastovariable);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = egreso.getId();
                if (findEgreso(id) == null) {
                    throw new NonexistentEntityException("The egreso with id " + id + " no longer exists.");
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
            Egreso egreso;
            try {
                egreso = em.getReference(Egreso.class, id);
                egreso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The egreso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Pagodesalario pagodesalarioOrphanCheck = egreso.getPagodesalario();
            if (pagodesalarioOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Egreso (" + egreso + ") cannot be destroyed since the Pagodesalario " + pagodesalarioOrphanCheck + " in its pagodesalario field has a non-nullable idegreso field.");
            }
            List<Gastovariable> gastovariableListOrphanCheck = egreso.getGastovariableList();
            for (Gastovariable gastovariableListOrphanCheckGastovariable : gastovariableListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Egreso (" + egreso + ") cannot be destroyed since the Gastovariable " + gastovariableListOrphanCheckGastovariable + " in its gastovariableList field has a non-nullable idEgreso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(egreso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Egreso> findEgresoEntities() {
        return findEgresoEntities(true, -1, -1);
    }

    public List<Egreso> findEgresoEntities(int maxResults, int firstResult) {
        return findEgresoEntities(false, maxResults, firstResult);
    }

    private List<Egreso> findEgresoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Egreso.class));
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

    public Egreso findEgreso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Egreso.class, id);
        } finally {
            em.close();
        }
    }

    public int getEgresoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Egreso> rt = cq.from(Egreso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
