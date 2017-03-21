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
import modelo.Maestros;

/**
 *
 * @author macbookpro
 */
public class MaestrosJpaController implements Serializable {

    public MaestrosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Maestros maestros) {
        if (maestros.getPagosdesalarioList() == null) {
            maestros.setPagosdesalarioList(new ArrayList<Pagosdesalario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pagosdesalario> attachedPagosdesalarioList = new ArrayList<Pagosdesalario>();
            for (Pagosdesalario pagosdesalarioListPagosdesalarioToAttach : maestros.getPagosdesalarioList()) {
                pagosdesalarioListPagosdesalarioToAttach = em.getReference(pagosdesalarioListPagosdesalarioToAttach.getClass(), pagosdesalarioListPagosdesalarioToAttach.getIdpagodesalario());
                attachedPagosdesalarioList.add(pagosdesalarioListPagosdesalarioToAttach);
            }
            maestros.setPagosdesalarioList(attachedPagosdesalarioList);
            em.persist(maestros);
            for (Pagosdesalario pagosdesalarioListPagosdesalario : maestros.getPagosdesalarioList()) {
                Maestros oldIdmaestroOfPagosdesalarioListPagosdesalario = pagosdesalarioListPagosdesalario.getIdmaestro();
                pagosdesalarioListPagosdesalario.setIdmaestro(maestros);
                pagosdesalarioListPagosdesalario = em.merge(pagosdesalarioListPagosdesalario);
                if (oldIdmaestroOfPagosdesalarioListPagosdesalario != null) {
                    oldIdmaestroOfPagosdesalarioListPagosdesalario.getPagosdesalarioList().remove(pagosdesalarioListPagosdesalario);
                    oldIdmaestroOfPagosdesalarioListPagosdesalario = em.merge(oldIdmaestroOfPagosdesalarioListPagosdesalario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Maestros maestros) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestros persistentMaestros = em.find(Maestros.class, maestros.getIdmaestro());
            List<Pagosdesalario> pagosdesalarioListOld = persistentMaestros.getPagosdesalarioList();
            List<Pagosdesalario> pagosdesalarioListNew = maestros.getPagosdesalarioList();
            List<String> illegalOrphanMessages = null;
            for (Pagosdesalario pagosdesalarioListOldPagosdesalario : pagosdesalarioListOld) {
                if (!pagosdesalarioListNew.contains(pagosdesalarioListOldPagosdesalario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pagosdesalario " + pagosdesalarioListOldPagosdesalario + " since its idmaestro field is not nullable.");
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
            maestros.setPagosdesalarioList(pagosdesalarioListNew);
            maestros = em.merge(maestros);
            for (Pagosdesalario pagosdesalarioListNewPagosdesalario : pagosdesalarioListNew) {
                if (!pagosdesalarioListOld.contains(pagosdesalarioListNewPagosdesalario)) {
                    Maestros oldIdmaestroOfPagosdesalarioListNewPagosdesalario = pagosdesalarioListNewPagosdesalario.getIdmaestro();
                    pagosdesalarioListNewPagosdesalario.setIdmaestro(maestros);
                    pagosdesalarioListNewPagosdesalario = em.merge(pagosdesalarioListNewPagosdesalario);
                    if (oldIdmaestroOfPagosdesalarioListNewPagosdesalario != null && !oldIdmaestroOfPagosdesalarioListNewPagosdesalario.equals(maestros)) {
                        oldIdmaestroOfPagosdesalarioListNewPagosdesalario.getPagosdesalarioList().remove(pagosdesalarioListNewPagosdesalario);
                        oldIdmaestroOfPagosdesalarioListNewPagosdesalario = em.merge(oldIdmaestroOfPagosdesalarioListNewPagosdesalario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = maestros.getIdmaestro();
                if (findMaestros(id) == null) {
                    throw new NonexistentEntityException("The maestros with id " + id + " no longer exists.");
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
            Maestros maestros;
            try {
                maestros = em.getReference(Maestros.class, id);
                maestros.getIdmaestro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The maestros with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pagosdesalario> pagosdesalarioListOrphanCheck = maestros.getPagosdesalarioList();
            for (Pagosdesalario pagosdesalarioListOrphanCheckPagosdesalario : pagosdesalarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Maestros (" + maestros + ") cannot be destroyed since the Pagosdesalario " + pagosdesalarioListOrphanCheckPagosdesalario + " in its pagosdesalarioList field has a non-nullable idmaestro field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(maestros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Maestros> findMaestrosEntities() {
        return findMaestrosEntities(true, -1, -1);
    }

    public List<Maestros> findMaestrosEntities(int maxResults, int firstResult) {
        return findMaestrosEntities(false, maxResults, firstResult);
    }

    private List<Maestros> findMaestrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Maestros.class));
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

    public Maestros findMaestros(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Maestros.class, id);
        } finally {
            em.close();
        }
    }

    public int getMaestrosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Maestros> rt = cq.from(Maestros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    //public <Maestros>
}
