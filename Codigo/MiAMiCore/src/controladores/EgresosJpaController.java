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
import modelo.Pagosdesalario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Egresos;

/**
 *
 * @author macbookpro
 */
public class EgresosJpaController implements Serializable {

    public EgresosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Egresos egresos) {
        if (egresos.getPagosdesalarioList() == null) {
            egresos.setPagosdesalarioList(new ArrayList<Pagosdesalario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pagosdesalario> attachedPagosdesalarioList = new ArrayList<Pagosdesalario>();
            for (Pagosdesalario pagosdesalarioListPagosdesalarioToAttach : egresos.getPagosdesalarioList()) {
                pagosdesalarioListPagosdesalarioToAttach = em.getReference(pagosdesalarioListPagosdesalarioToAttach.getClass(), pagosdesalarioListPagosdesalarioToAttach.getIdpagodesalario());
                attachedPagosdesalarioList.add(pagosdesalarioListPagosdesalarioToAttach);
            }
            egresos.setPagosdesalarioList(attachedPagosdesalarioList);
            em.persist(egresos);
            for (Pagosdesalario pagosdesalarioListPagosdesalario : egresos.getPagosdesalarioList()) {
                Egresos oldIdegresoOfPagosdesalarioListPagosdesalario = pagosdesalarioListPagosdesalario.getIdegreso();
                pagosdesalarioListPagosdesalario.setIdegreso(egresos);
                pagosdesalarioListPagosdesalario = em.merge(pagosdesalarioListPagosdesalario);
                if (oldIdegresoOfPagosdesalarioListPagosdesalario != null) {
                    oldIdegresoOfPagosdesalarioListPagosdesalario.getPagosdesalarioList().remove(pagosdesalarioListPagosdesalario);
                    oldIdegresoOfPagosdesalarioListPagosdesalario = em.merge(oldIdegresoOfPagosdesalarioListPagosdesalario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Egresos egresos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Egresos persistentEgresos = em.find(Egresos.class, egresos.getIdegresos());
            List<Pagosdesalario> pagosdesalarioListOld = persistentEgresos.getPagosdesalarioList();
            List<Pagosdesalario> pagosdesalarioListNew = egresos.getPagosdesalarioList();
            List<String> illegalOrphanMessages = null;
            for (Pagosdesalario pagosdesalarioListOldPagosdesalario : pagosdesalarioListOld) {
                if (!pagosdesalarioListNew.contains(pagosdesalarioListOldPagosdesalario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pagosdesalario " + pagosdesalarioListOldPagosdesalario + " since its idegreso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Pagosdesalario> attachedPagosdesalarioListNew = new ArrayList<Pagosdesalario>();
            for (Pagosdesalario pagosdesalarioListNewPagosdesalarioToAttach : pagosdesalarioListNew) {
                pagosdesalarioListNewPagosdesalarioToAttach = em.getReference(pagosdesalarioListNewPagosdesalarioToAttach.getClass(), pagosdesalarioListNewPagosdesalarioToAttach.getIdpagodesalario());
                attachedPagosdesalarioListNew.add(pagosdesalarioListNewPagosdesalarioToAttach);
            }
            pagosdesalarioListNew = attachedPagosdesalarioListNew;
            egresos.setPagosdesalarioList(pagosdesalarioListNew);
            egresos = em.merge(egresos);
            for (Pagosdesalario pagosdesalarioListNewPagosdesalario : pagosdesalarioListNew) {
                if (!pagosdesalarioListOld.contains(pagosdesalarioListNewPagosdesalario)) {
                    Egresos oldIdegresoOfPagosdesalarioListNewPagosdesalario = pagosdesalarioListNewPagosdesalario.getIdegreso();
                    pagosdesalarioListNewPagosdesalario.setIdegreso(egresos);
                    pagosdesalarioListNewPagosdesalario = em.merge(pagosdesalarioListNewPagosdesalario);
                    if (oldIdegresoOfPagosdesalarioListNewPagosdesalario != null && !oldIdegresoOfPagosdesalarioListNewPagosdesalario.equals(egresos)) {
                        oldIdegresoOfPagosdesalarioListNewPagosdesalario.getPagosdesalarioList().remove(pagosdesalarioListNewPagosdesalario);
                        oldIdegresoOfPagosdesalarioListNewPagosdesalario = em.merge(oldIdegresoOfPagosdesalarioListNewPagosdesalario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = egresos.getIdegresos();
                if (findEgresos(id) == null) {
                    throw new NonexistentEntityException("The egresos with id " + id + " no longer exists.");
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
            Egresos egresos;
            try {
                egresos = em.getReference(Egresos.class, id);
                egresos.getIdegresos();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The egresos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pagosdesalario> pagosdesalarioListOrphanCheck = egresos.getPagosdesalarioList();
            for (Pagosdesalario pagosdesalarioListOrphanCheckPagosdesalario : pagosdesalarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Egresos (" + egresos + ") cannot be destroyed since the Pagosdesalario " + pagosdesalarioListOrphanCheckPagosdesalario + " in its pagosdesalarioList field has a non-nullable idegreso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(egresos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Egresos> findEgresosEntities() {
        return findEgresosEntities(true, -1, -1);
    }

    public List<Egresos> findEgresosEntities(int maxResults, int firstResult) {
        return findEgresosEntities(false, maxResults, firstResult);
    }

    private List<Egresos> findEgresosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Egresos.class));
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

    public Egresos findEgresos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Egresos.class, id);
        } finally {
            em.close();
        }
    }

    public int getEgresosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Egresos> rt = cq.from(Egresos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
