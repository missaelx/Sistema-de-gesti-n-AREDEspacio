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
import modelo.GrupoClase;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.TipoDanza;

/**
 *
 * @author macbookpro
 */
public class TipoDanzaJpaController implements Serializable {

    public TipoDanzaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoDanza tipoDanza) {
        if (tipoDanza.getGrupoClaseList() == null) {
            tipoDanza.setGrupoClaseList(new ArrayList<GrupoClase>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<GrupoClase> attachedGrupoClaseList = new ArrayList<GrupoClase>();
            for (GrupoClase grupoClaseListGrupoClaseToAttach : tipoDanza.getGrupoClaseList()) {
                grupoClaseListGrupoClaseToAttach = em.getReference(grupoClaseListGrupoClaseToAttach.getClass(), grupoClaseListGrupoClaseToAttach.getId());
                attachedGrupoClaseList.add(grupoClaseListGrupoClaseToAttach);
            }
            tipoDanza.setGrupoClaseList(attachedGrupoClaseList);
            em.persist(tipoDanza);
            for (GrupoClase grupoClaseListGrupoClase : tipoDanza.getGrupoClaseList()) {
                TipoDanza oldIdTipoDanzaOfGrupoClaseListGrupoClase = grupoClaseListGrupoClase.getIdTipoDanza();
                grupoClaseListGrupoClase.setIdTipoDanza(tipoDanza);
                grupoClaseListGrupoClase = em.merge(grupoClaseListGrupoClase);
                if (oldIdTipoDanzaOfGrupoClaseListGrupoClase != null) {
                    oldIdTipoDanzaOfGrupoClaseListGrupoClase.getGrupoClaseList().remove(grupoClaseListGrupoClase);
                    oldIdTipoDanzaOfGrupoClaseListGrupoClase = em.merge(oldIdTipoDanzaOfGrupoClaseListGrupoClase);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoDanza tipoDanza) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoDanza persistentTipoDanza = em.find(TipoDanza.class, tipoDanza.getId());
            List<GrupoClase> grupoClaseListOld = persistentTipoDanza.getGrupoClaseList();
            List<GrupoClase> grupoClaseListNew = tipoDanza.getGrupoClaseList();
            List<String> illegalOrphanMessages = null;
            for (GrupoClase grupoClaseListOldGrupoClase : grupoClaseListOld) {
                if (!grupoClaseListNew.contains(grupoClaseListOldGrupoClase)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GrupoClase " + grupoClaseListOldGrupoClase + " since its idTipoDanza field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<GrupoClase> attachedGrupoClaseListNew = new ArrayList<GrupoClase>();
            for (GrupoClase grupoClaseListNewGrupoClaseToAttach : grupoClaseListNew) {
                grupoClaseListNewGrupoClaseToAttach = em.getReference(grupoClaseListNewGrupoClaseToAttach.getClass(), grupoClaseListNewGrupoClaseToAttach.getId());
                attachedGrupoClaseListNew.add(grupoClaseListNewGrupoClaseToAttach);
            }
            grupoClaseListNew = attachedGrupoClaseListNew;
            tipoDanza.setGrupoClaseList(grupoClaseListNew);
            tipoDanza = em.merge(tipoDanza);
            for (GrupoClase grupoClaseListNewGrupoClase : grupoClaseListNew) {
                if (!grupoClaseListOld.contains(grupoClaseListNewGrupoClase)) {
                    TipoDanza oldIdTipoDanzaOfGrupoClaseListNewGrupoClase = grupoClaseListNewGrupoClase.getIdTipoDanza();
                    grupoClaseListNewGrupoClase.setIdTipoDanza(tipoDanza);
                    grupoClaseListNewGrupoClase = em.merge(grupoClaseListNewGrupoClase);
                    if (oldIdTipoDanzaOfGrupoClaseListNewGrupoClase != null && !oldIdTipoDanzaOfGrupoClaseListNewGrupoClase.equals(tipoDanza)) {
                        oldIdTipoDanzaOfGrupoClaseListNewGrupoClase.getGrupoClaseList().remove(grupoClaseListNewGrupoClase);
                        oldIdTipoDanzaOfGrupoClaseListNewGrupoClase = em.merge(oldIdTipoDanzaOfGrupoClaseListNewGrupoClase);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoDanza.getId();
                if (findTipoDanza(id) == null) {
                    throw new NonexistentEntityException("The tipoDanza with id " + id + " no longer exists.");
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
            TipoDanza tipoDanza;
            try {
                tipoDanza = em.getReference(TipoDanza.class, id);
                tipoDanza.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoDanza with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<GrupoClase> grupoClaseListOrphanCheck = tipoDanza.getGrupoClaseList();
            for (GrupoClase grupoClaseListOrphanCheckGrupoClase : grupoClaseListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoDanza (" + tipoDanza + ") cannot be destroyed since the GrupoClase " + grupoClaseListOrphanCheckGrupoClase + " in its grupoClaseList field has a non-nullable idTipoDanza field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoDanza);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoDanza> findTipoDanzaEntities() {
        return findTipoDanzaEntities(true, -1, -1);
    }

    public List<TipoDanza> findTipoDanzaEntities(int maxResults, int firstResult) {
        return findTipoDanzaEntities(false, maxResults, firstResult);
    }

    private List<TipoDanza> findTipoDanzaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoDanza.class));
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

    public TipoDanza findTipoDanza(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoDanza.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoDanzaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoDanza> rt = cq.from(TipoDanza.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
