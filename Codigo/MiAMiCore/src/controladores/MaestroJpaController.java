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
import modelo.Maestro;
import modelo.Pagodesalario;

/**
 *
 * @author macbookpro
 */
public class MaestroJpaController implements Serializable {

    public MaestroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Maestro maestro) {
        if (maestro.getGrupoClaseList() == null) {
            maestro.setGrupoClaseList(new ArrayList<GrupoClase>());
        }
        if (maestro.getPagodesalarioList() == null) {
            maestro.setPagodesalarioList(new ArrayList<Pagodesalario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<GrupoClase> attachedGrupoClaseList = new ArrayList<GrupoClase>();
            for (GrupoClase grupoClaseListGrupoClaseToAttach : maestro.getGrupoClaseList()) {
                grupoClaseListGrupoClaseToAttach = em.getReference(grupoClaseListGrupoClaseToAttach.getClass(), grupoClaseListGrupoClaseToAttach.getId());
                attachedGrupoClaseList.add(grupoClaseListGrupoClaseToAttach);
            }
            maestro.setGrupoClaseList(attachedGrupoClaseList);
            List<Pagodesalario> attachedPagodesalarioList = new ArrayList<Pagodesalario>();
            for (Pagodesalario pagodesalarioListPagodesalarioToAttach : maestro.getPagodesalarioList()) {
                pagodesalarioListPagodesalarioToAttach = em.getReference(pagodesalarioListPagodesalarioToAttach.getClass(), pagodesalarioListPagodesalarioToAttach.getId());
                attachedPagodesalarioList.add(pagodesalarioListPagodesalarioToAttach);
            }
            maestro.setPagodesalarioList(attachedPagodesalarioList);
            em.persist(maestro);
            for (GrupoClase grupoClaseListGrupoClase : maestro.getGrupoClaseList()) {
                Maestro oldIdMaestroOfGrupoClaseListGrupoClase = grupoClaseListGrupoClase.getIdMaestro();
                grupoClaseListGrupoClase.setIdMaestro(maestro);
                grupoClaseListGrupoClase = em.merge(grupoClaseListGrupoClase);
                if (oldIdMaestroOfGrupoClaseListGrupoClase != null) {
                    oldIdMaestroOfGrupoClaseListGrupoClase.getGrupoClaseList().remove(grupoClaseListGrupoClase);
                    oldIdMaestroOfGrupoClaseListGrupoClase = em.merge(oldIdMaestroOfGrupoClaseListGrupoClase);
                }
            }
            for (Pagodesalario pagodesalarioListPagodesalario : maestro.getPagodesalarioList()) {
                Maestro oldIdmaestroOfPagodesalarioListPagodesalario = pagodesalarioListPagodesalario.getIdmaestro();
                pagodesalarioListPagodesalario.setIdmaestro(maestro);
                pagodesalarioListPagodesalario = em.merge(pagodesalarioListPagodesalario);
                if (oldIdmaestroOfPagodesalarioListPagodesalario != null) {
                    oldIdmaestroOfPagodesalarioListPagodesalario.getPagodesalarioList().remove(pagodesalarioListPagodesalario);
                    oldIdmaestroOfPagodesalarioListPagodesalario = em.merge(oldIdmaestroOfPagodesalarioListPagodesalario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Maestro maestro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestro persistentMaestro = em.find(Maestro.class, maestro.getId());
            List<GrupoClase> grupoClaseListOld = persistentMaestro.getGrupoClaseList();
            List<GrupoClase> grupoClaseListNew = maestro.getGrupoClaseList();
            List<Pagodesalario> pagodesalarioListOld = persistentMaestro.getPagodesalarioList();
            List<Pagodesalario> pagodesalarioListNew = maestro.getPagodesalarioList();
            List<String> illegalOrphanMessages = null;
            for (GrupoClase grupoClaseListOldGrupoClase : grupoClaseListOld) {
                if (!grupoClaseListNew.contains(grupoClaseListOldGrupoClase)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GrupoClase " + grupoClaseListOldGrupoClase + " since its idMaestro field is not nullable.");
                }
            }
            for (Pagodesalario pagodesalarioListOldPagodesalario : pagodesalarioListOld) {
                if (!pagodesalarioListNew.contains(pagodesalarioListOldPagodesalario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pagodesalario " + pagodesalarioListOldPagodesalario + " since its idmaestro field is not nullable.");
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
            maestro.setGrupoClaseList(grupoClaseListNew);
            List<Pagodesalario> attachedPagodesalarioListNew = new ArrayList<Pagodesalario>();
            for (Pagodesalario pagodesalarioListNewPagodesalarioToAttach : pagodesalarioListNew) {
                pagodesalarioListNewPagodesalarioToAttach = em.getReference(pagodesalarioListNewPagodesalarioToAttach.getClass(), pagodesalarioListNewPagodesalarioToAttach.getId());
                attachedPagodesalarioListNew.add(pagodesalarioListNewPagodesalarioToAttach);
            }
            pagodesalarioListNew = attachedPagodesalarioListNew;
            maestro.setPagodesalarioList(pagodesalarioListNew);
            maestro = em.merge(maestro);
            for (GrupoClase grupoClaseListNewGrupoClase : grupoClaseListNew) {
                if (!grupoClaseListOld.contains(grupoClaseListNewGrupoClase)) {
                    Maestro oldIdMaestroOfGrupoClaseListNewGrupoClase = grupoClaseListNewGrupoClase.getIdMaestro();
                    grupoClaseListNewGrupoClase.setIdMaestro(maestro);
                    grupoClaseListNewGrupoClase = em.merge(grupoClaseListNewGrupoClase);
                    if (oldIdMaestroOfGrupoClaseListNewGrupoClase != null && !oldIdMaestroOfGrupoClaseListNewGrupoClase.equals(maestro)) {
                        oldIdMaestroOfGrupoClaseListNewGrupoClase.getGrupoClaseList().remove(grupoClaseListNewGrupoClase);
                        oldIdMaestroOfGrupoClaseListNewGrupoClase = em.merge(oldIdMaestroOfGrupoClaseListNewGrupoClase);
                    }
                }
            }
            for (Pagodesalario pagodesalarioListNewPagodesalario : pagodesalarioListNew) {
                if (!pagodesalarioListOld.contains(pagodesalarioListNewPagodesalario)) {
                    Maestro oldIdmaestroOfPagodesalarioListNewPagodesalario = pagodesalarioListNewPagodesalario.getIdmaestro();
                    pagodesalarioListNewPagodesalario.setIdmaestro(maestro);
                    pagodesalarioListNewPagodesalario = em.merge(pagodesalarioListNewPagodesalario);
                    if (oldIdmaestroOfPagodesalarioListNewPagodesalario != null && !oldIdmaestroOfPagodesalarioListNewPagodesalario.equals(maestro)) {
                        oldIdmaestroOfPagodesalarioListNewPagodesalario.getPagodesalarioList().remove(pagodesalarioListNewPagodesalario);
                        oldIdmaestroOfPagodesalarioListNewPagodesalario = em.merge(oldIdmaestroOfPagodesalarioListNewPagodesalario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = maestro.getId();
                if (findMaestro(id) == null) {
                    throw new NonexistentEntityException("The maestro with id " + id + " no longer exists.");
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
            Maestro maestro;
            try {
                maestro = em.getReference(Maestro.class, id);
                maestro.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The maestro with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<GrupoClase> grupoClaseListOrphanCheck = maestro.getGrupoClaseList();
            for (GrupoClase grupoClaseListOrphanCheckGrupoClase : grupoClaseListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Maestro (" + maestro + ") cannot be destroyed since the GrupoClase " + grupoClaseListOrphanCheckGrupoClase + " in its grupoClaseList field has a non-nullable idMaestro field.");
            }
            List<Pagodesalario> pagodesalarioListOrphanCheck = maestro.getPagodesalarioList();
            for (Pagodesalario pagodesalarioListOrphanCheckPagodesalario : pagodesalarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Maestro (" + maestro + ") cannot be destroyed since the Pagodesalario " + pagodesalarioListOrphanCheckPagodesalario + " in its pagodesalarioList field has a non-nullable idmaestro field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(maestro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Maestro> findMaestroEntities() {
        return findMaestroEntities(true, -1, -1);
    }

    public List<Maestro> findMaestroEntities(int maxResults, int firstResult) {
        return findMaestroEntities(false, maxResults, firstResult);
    }

    private List<Maestro> findMaestroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Maestro.class));
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

    public Maestro findMaestro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Maestro.class, id);
        } finally {
            em.close();
        }
    }

    public int getMaestroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Maestro> rt = cq.from(Maestro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
